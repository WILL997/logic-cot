import glob
import os
import subprocess
import re
import shutil
import tempfile
from datetime import datetime
from config import *


class TestRunner:

    #用于设置类的基本属性并进行预处理，主要用于配置代码覆盖率工具和准备测试环境。
    def __init__(self, test_path, target_path, tool="cobertura"):
        """
        :param tool: coverage tool (Only support cobertura or jacoco)
        :param test_path: test cases directory path e.g.:
        /data/share/TestGPT_ASE/result/scope_test%20230414210243%d3_1/ (all test)
        /data/share/TestGPT_ASE/result/scope_test%20230414210243%d3_1/1460%lang_1_f%ToStringBuilder%append%d3/5 (single test)
        :param target_path: target project path
        """
        self.coverage_tool = tool
        self.test_path = os.path.abspath(test_path)
        self.target_path = os.path.abspath(target_path)
        self.config_base_dir = os.path.dirname(os.path.abspath(__file__))

        # Preprocess
        self.java_version_major = self.detect_java_major()
        self.build_system = self.detect_build_system(self.target_path)
        self.build_dir_name = self.get_default_build_dir_name()
        self.ensure_project_ready()
        self.dependencies = self.make_dependency()
        self.class_dirs = self.process_single_repo()
        self.build_dir = os.pathsep.join(self.class_dirs)

        self.COMPILE_ERROR = 0
        self.TEST_RUN_ERROR = 0

    #用于运行单个方法测试，负责从测试用例编译、运行到结果报告的完整流程。
    # 适用于指定路径下的测试用例（如单个 Java 方法测试）的自动化执行。
    def start_single_test(self):
        """
        Run a single method test case with a thread.
        tests directory path, e.g.:
        /data/share/TestGPT_ASE/result/scope_test%20230414210243%d3_1/1460%lang_1_f%ToStringBuilder%append%d3/5
        """
        #print("start_single_test")
        temp_dir = os.path.join(self.test_path, "temp")
        compiled_test_dir = os.path.join(self.test_path, "runtemp")
        os.makedirs(compiled_test_dir, exist_ok=True)
        try:
            self.instrument(compiled_test_dir, compiled_test_dir)
            test_file = os.path.abspath(glob.glob(temp_dir + '/*.java')[0])
            #print(test_file)
            compiler_output = os.path.join(temp_dir, 'compile_error')
            test_output = os.path.join(temp_dir, 'runtime_error')
            #print("temp_dir"+temp_dir)
            if not self.run_single_test(test_file, compiled_test_dir, compiler_output, test_output):
                return False
            else:
                self.report(compiled_test_dir, temp_dir)
        except Exception as e:
            print(e)
            return False
        return True

    # 初始化配置并执行所有测试用例。它自动设置测试目录结构，包括编译输出、
    # 测试结果和报告的路径，适用于运行整个测试套件的场景。
    def start_all_test(self):
        """
        Initialize configurations and run all tests
        """
        date = datetime.now().strftime("%Y%m%d%H%M%S")

        # Directories for the test cases, outputs, and reports
        tests_dir = os.path.join(self.target_path, f"tests%{date}")
        compiler_output_dir = os.path.join(tests_dir, "compiler_output")
        test_output_dir = os.path.join(tests_dir, "test_output")
        report_dir = os.path.join(tests_dir, "report")

        compiler_output = os.path.join(compiler_output_dir, "CompilerOutput")
        test_output = os.path.join(test_output_dir, "TestOutput")
        compiled_test_dir = os.path.join(tests_dir, "tests_ChatGPT")

        self.copy_tests(tests_dir)
        return self.run_all_tests(tests_dir, compiled_test_dir, compiler_output, test_output, report_dir)

    #运行项目中的所有测试用例，并生成覆盖率报告。它通过逐个编译和执行测试用例，统计编译和运行过程中的错误，最终输出测试的统计结果。
    def run_all_tests(self, tests_dir, compiled_test_dir, compiler_output, test_output, report_dir):
        """
        Run all test cases in a project.
        """
        tests = os.path.join(tests_dir, "test_cases")
        self.instrument(compiled_test_dir, compiled_test_dir)
        total_compile = 0
        total_test_run = 0
        for t in range(1, 1 + test_number):
            print("Processing attempt: ", str(t))
            for test_case_file in os.listdir(tests):
                if str(t) != test_case_file.split('_')[-1].replace('Test.java', ''):
                    continue
                total_compile += 1
                try:
                    test_file = os.path.join(tests, test_case_file)
                    self.run_single_test(test_file, compiled_test_dir, compiler_output, test_output)
                except Exception as e:
                    print(e)
            self.report(compiled_test_dir, os.path.join(report_dir, str(t)))
            total_test_run = total_compile - self.COMPILE_ERROR
            print("COMPILE TOTAL COUNT:", total_compile)
            print("COMPILE ERROR COUNT:", self.COMPILE_ERROR)
            print("TEST RUN TOTAL COUNT:", total_test_run)
            print("TEST RUN ERROR COUNT:", self.TEST_RUN_ERROR)
            print("\n")
        return total_compile, total_test_run

    #编译并运行单个测试用例，同时捕获运行结果和潜在的运行时错误。
    def run_single_test(self, test_file, compiled_test_dir, compiler_output, test_output):
        """
        Run a test case.
        :return: Whether it is successful or no.
        """
        #print("Running single test")
        #在这一步先编译测试用例，如果失败就直接生成对应 temp/compile_error.txt文件  成功的话就进行下一步运行
        if not self.compile(test_file, compiled_test_dir, compiler_output):
            return False
        #决定运行时错误输出文件名
        #常见场景temp/runtime_error
        if os.path.basename(test_output) == 'runtime_error':
            test_output_file = f"{test_output}.txt"
        #如果是批量模式/多测试共享目录时（比如 all_test 模式）：test_output 可能是某个目录 + 通用前缀，它会变成：
        else:
            test_output_file = f"{test_output}-{os.path.basename(test_file)}.txt"
        cmd = self.java_cmd(compiled_test_dir, test_file)
        try:
            result = subprocess.run(cmd, timeout=TIMEOUT,
                                    stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            if result.returncode != 0:
                self.TEST_RUN_ERROR += 1
                self.export_runtime_output(result, test_output_file)
                return False
        except subprocess.TimeoutExpired:
            # print(Fore.RED + "TIME OUT!", Style.RESET_ALL)
            return False
        return True

    #主要功能是将测试用例运行时的输出结果导出到指定的文件中，包括标准输出和错误信息。
    @staticmethod
    def export_runtime_output(result, test_output_file):
        with open(test_output_file, "w") as f:
            f.write(result.stdout)
            error_msg = result.stderr
            error_msg = re.sub(r'log4j:WARN.*\n?', '', error_msg)
            if error_msg != '':
                f.write(error_msg)

    #编译单个测试用例文件，并将编译输出结果保存到指定文件中
    def compile(self, test_file, compiled_test_dir, compiler_output):
        """
        Compile a test case.
        :param test_file:
        :param compiled_test_dir: the directory to store compiled tests
        :param compiler_output:
        """
        os.makedirs(compiled_test_dir, exist_ok=True)
        cmd = self.javac_cmd(compiled_test_dir, test_file)
        #print("compiled_cmd")
        #print(cmd)
        result = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        #print("result")
        #print(result)
        if result.returncode != 0:
            self.COMPILE_ERROR += 1
            if os.path.basename(compiler_output) == 'compile_error':
                compiler_output_file = f"{compiler_output}.txt"
            else:
                compiler_output_file = f"{compiler_output}-{os.path.basename(test_file)}.txt"
            with open(compiler_output_file, "w") as f:
                f.write(result.stdout)
                f.write(result.stderr)
            return False
        return True

    #作用是获取目标项目的所有构建目录路径，特别是处理包含子模块的多模块项目
    def process_single_repo(self):
        """
        Return the all build directories of target repository
        """
        if self.build_system == "maven":
            if self.has_submodule(self.target_path):
                modules = self.get_submodule(self.target_path)
                return [os.path.join(self.target_path, module, self.build_dir_name) for module in modules]
            return [os.path.join(self.target_path, self.build_dir_name)]

        if self.build_system == "gradle":
            class_dirs = []
            candidates = self.get_gradle_module_dirs(self.target_path)
            if not candidates:
                candidates = [self.target_path]
            for module_dir in candidates:
                java_main = os.path.join(module_dir, "build", "classes", "java", "main")
                kotlin_main = os.path.join(module_dir, "build", "classes", "kotlin", "main")
                if os.path.isdir(java_main):
                    class_dirs.append(java_main)
                if os.path.isdir(kotlin_main):
                    class_dirs.append(kotlin_main)
            if class_dirs:
                return class_dirs
            return [os.path.join(self.target_path, "build", "classes", "java", "main")]

        return [os.path.join(self.target_path, self.build_dir_name)]

    # Java 测试文件中提取 package 声明，返回文件的包路径。
    @staticmethod
    def get_package(test_file):
        with open(test_file, "r") as f:
            first_line = f.readline()

        package = first_line.strip().replace("package ", "").replace(";", "")
        return package

    #判断给定路径是否是一个 Maven 模块。
    # 通过检查路径下是否存在 pom.xml 文件和编译目录 target/classes 来确定。
    @staticmethod
    def is_module(project_path):
        """
        If the path has a pom.xml file and target/classes compiled, a module.
        """
        if not os.path.isdir(project_path):
            return False
        if 'pom.xml' in os.listdir(project_path) and 'target' in os.listdir(project_path):
            return True
        return False

    #用于获取给定项目路径下的所有子模块。通过判断每个子目录是否是 Maven 模块来筛选子模块
    def get_submodule(self, project_path):
        """
        Get all modules in given project.
        :return: module list
        """
        return [d for d in os.listdir(project_path) if self.is_module(os.path.join(project_path, d))]

    #用于检查给定的项目是否包含子模块。如果项目中至少有一个符合 Maven 模块条件的子目录，则认为该项目包含子模块。
    def has_submodule(self, project_path):
        """
        Is a project composed by submodules, e.g., gson
        """
        for dir in os.listdir(project_path):
            if self.is_module(os.path.join(project_path, dir)):
                return True
        return False

    #该方法生成用于编译测试文件的 javac 命令。它将构建一个包含所有依赖项和类路径的命令，
    # 并返回一个列表，包含可以用于运行 subprocess.run() 的命令。
    def javac_cmd(self, compiled_test_dir, test_file):
        javac_exe = self.find_java_tool("javac")
        classpath = os.pathsep.join(
            self.normalize_classpath_entries(
                self.resolve_classpath_value(JUNIT_JAR),
                self.resolve_classpath_value(MOCKITO_JAR),
                self.resolve_classpath_value(LOG4J_JAR),
                self.build_dir,
                self.dependencies,
                ".",
            )
        )
        classpath_file = os.path.join(compiled_test_dir, 'classpath.txt')
        self.export_classpath(classpath_file, classpath)
        return [javac_exe, "-d", compiled_test_dir, f"@{classpath_file}", test_file]

    
    #默认覆盖工具为cobertura
    #生成用于运行测试并收集覆盖率的 Java 命令，支持不同的覆盖工具（如 Cobertura 或 JaCoCo）。
    # 它根据指定的覆盖工具（self.coverage_tool）返回相应的命令。
    def java_cmd(self, compiled_test_dir, test_file):
        full_test_name = self.get_full_name(test_file)
        java_exe = self.find_java_tool("java")
        classpath = os.pathsep.join(
            self.normalize_classpath_entries(
                self.get_cobertura_java_classpath(),
                os.path.join(compiled_test_dir, "instrumented"),
                compiled_test_dir,
                self.resolve_classpath_value(JUNIT_JAR),
                self.resolve_classpath_value(MOCKITO_JAR),
                self.resolve_classpath_value(LOG4J_JAR),
                self.build_dir,
                self.dependencies,
                ".",
            )
        )
        classpath_file = os.path.join(compiled_test_dir, 'classpath.txt')
        use_argfile = self.java_version_major >= 9
        if use_argfile:
            self.export_classpath(classpath_file, classpath)
        if self.coverage_tool == "cobertura":
            if use_argfile:
                cp_args = [f"@{classpath_file}"]
            else:
                cp_args = ["-cp", classpath]
            return [java_exe, *cp_args,
                    f"-Dnet.sourceforge.cobertura.datafile={compiled_test_dir}/cobertura.ser",
                    "org.junit.platform.console.ConsoleLauncher", "--disable-banner", "--disable-ansi-colors",
                    "--fail-if-no-tests", "--details=none", "--select-class", full_test_name]
        else:  # self.coverage_tool == "jacoco"
            if use_argfile:
                cp_args = [f"@{classpath_file}"]
            else:
                cp_args = ["-cp", classpath]
            jacoco_agent = self.resolve_path(JACOCO_AGENT)
            return [java_exe, f"-javaagent:{jacoco_agent}=destfile={compiled_test_dir}/jacoco.exec",
                    *cp_args,
                    "org.junit.platform.console.ConsoleLauncher", "--disable-banner", "--disable-ansi-colors",
                    "--fail-if-no-tests", "--details=none", "--select-class", full_test_name]

    #作用是将类路径（classpath）写入到指定的文件（classpath_file）中，以便后续的 Java 命令可以通过该文件使用类路径。
    @staticmethod
    def export_classpath(classpath_file, classpath):
        with open(classpath_file, 'w') as f:
            classpath = "-cp " + classpath
            f.write(classpath)
        return

    #生成 Java 测试文件的完整类名。它从文件路径中提取包名（如果存在），并结合文件名（去除扩展名）来形成类的完整名称。
    def get_full_name(self, test_file):
        package = self.get_package(test_file)
        test_case = os.path.splitext(os.path.basename(test_file))[0]
        if package != '':
            return f"{package}.{test_case}"
        else:
            return test_case

    #生成 Java 测试文件的完整类名。它从文件路径中提取包名（如果存在），并结合文件名（去除扩展名）来形成类的完整名称。
    def instrument(self, instrument_dir, datafile_dir):
        """
        Use cobertura scripts to instrument compiled class.
        Generate 'instrumented' directory.
        """
        if self.coverage_tool == "jacoco":
            return
        os.makedirs(instrument_dir, exist_ok=True)
        os.makedirs(datafile_dir, exist_ok=True)
        if 'instrumented' in os.listdir(instrument_dir):
            return
        target_classes = self.get_instrument_target_class_dirs()
        if not target_classes:
            self.write_text_file(
                os.path.join(datafile_dir, "instrument_error.txt"),
                f"No instrument target classes found. build_system={self.build_system}, build_dirs={self.class_dirs}\n",
            )
            return
        result = self.run_cobertura_instrument(instrument_dir, datafile_dir, target_classes)
        instrumented_root = os.path.join(instrument_dir, "instrumented")
        instrumented_classes = glob.glob(os.path.join(instrumented_root, "**", "*.class"), recursive=True)
        error_file = os.path.join(datafile_dir, "instrument_error.txt")
        if result.returncode != 0 or not instrumented_classes:
            self.write_text_file(
                error_file,
                (result.stdout or "") + "\n" + (result.stderr or ""),
            )
        else:
            if os.path.exists(error_file):
                try:
                    os.remove(error_file)
                except Exception:
                    pass

    #根据所选的覆盖率工具（Cobertura 或 JaCoCo）生成代码覆盖率报告。它会根据指定的工具和数据文件生成报告，并将其保存在指定的目录中。
    def report(self, datafile_dir, report_dir):
        """
        Generate coverage report by given coverage tool.
        """
        os.makedirs(report_dir, exist_ok=True)
        if self.coverage_tool == "cobertura":
            result = self.run_cobertura_report(datafile_dir, report_dir)
            error_file = os.path.join(report_dir, "coverage_report_error.txt")
            if result.returncode != 0:
                self.write_text_file(error_file, (result.stdout or "") + "\n" + (result.stderr or ""))
            else:
                if os.path.exists(error_file):
                    try:
                        os.remove(error_file)
                    except Exception:
                        pass
        else:
            java_exe = self.find_java_tool("java")
            jacoco_cli = self.resolve_path(JACOCO_CLI)
            build_list = [b for b in self.build_dir.split(os.pathsep) if b]
            classfiles_args = []
            for build in build_list:
                classfiles_args.extend(["--classfiles", build])
            for jar in self.find_project_output_jars():
                classfiles_args.extend(["--classfiles", jar])
            result = subprocess.run(
                [java_exe, "-jar", jacoco_cli, "report", f"{datafile_dir}/jacoco.exec", *classfiles_args,
                 "--csv", os.path.join(report_dir, "coverage.csv")],
                stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            if result.returncode != 0:
                with open(os.path.join(report_dir, "coverage_report_error.txt"), "w", encoding="utf-8") as f:
                    f.write((result.stdout or b"").decode(errors="replace") if isinstance(result.stdout, bytes) else (result.stdout or ""))
                    f.write("\n")
                    f.write((result.stderr or b"").decode(errors="replace") if isinstance(result.stderr, bytes) else (result.stderr or ""))

    #方法通过运行 Maven 命令来下载项目的依赖 JAR 文件，并将其路径作为类路径的一部分返回。
    def make_dependency(self):
        """
        Generate runtime dependencies of a given project
        """
        if self.build_system == "maven":
            mvn_dependency_dir = 'target/dependency'
            deps = []
            if not self.has_made():
                subprocess.run(
                    f"mvn dependency:copy-dependencies -DoutputDirectory={mvn_dependency_dir} -f {self.target_path}/pom.xml",
                    shell=True,
                    stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                subprocess.run(f"mvn install -DskipTests -f {self.target_path}/pom.xml", shell=True,
                               stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

            dep_jars = glob.glob(os.path.join(self.target_path, "**", "*.jar"), recursive=True)
            deps.extend(dep_jars)
            deps = list(set(deps))
            return os.pathsep.join(deps)

        if self.build_system == "gradle":
            cp = self.get_gradle_main_runtime_classpath()
            if cp:
                return cp
            jars = self.find_local_jars()
            return os.pathsep.join(jars)

        jars = self.find_local_jars()
        return os.pathsep.join(jars)

    #检查一个给定的 Maven 项目是否已经生成过依赖。
    # 具体来说，它检查项目目录中是否存在 Maven 构建生成的文件夹及依赖。
    def has_made(self):
        """
        If the project has made before
        """
        for dirpath, dirnames, filenames in os.walk(self.target_path):
            if 'pom.xml' in filenames and 'target' in dirnames:
                target = os.path.join(dirpath, 'target')
                if 'dependency' in os.listdir(target):
                    return True
        return False

    #方法的目的是将给定项目的测试用例复制到指定的目标路径，以便后续进行测试执行。
    def copy_tests(self, target_dir):
        """
        Copy test cases of given project to target path for running.
        :param target_dir: path to target directory used to store test cases
        """
        tests = glob.glob(self.test_path + "/**/*Test.java", recursive=True)
        target_project = os.path.basename(self.target_path.rstrip('/'))
        _ = [os.makedirs(os.path.join(target_dir, dir_name), exist_ok=True) for dir_name in
             ("test_cases", "compiler_output", "test_output", "report")]
        print("Copying tests to ", target_project, '...')
        for tc in tests:
            # tc should be 'pathto/project/testcase'.
            tc_parts = os.path.normpath(tc).split(os.sep)
            if len(tc_parts) < 4:
                continue
            tc_project_token = tc_parts[-4]
            if '%' not in tc_project_token:
                continue
            tc_project = tc_project_token.split('%', 2)[1]
            if tc_project != target_project or \
                    not os.path.exists(self.target_path):
                continue
            shutil.copy2(tc, os.path.join(target_dir, 'test_cases', os.path.basename(tc)))

    @staticmethod
    def detect_build_system(target_path):
        target_path = os.path.abspath(target_path)
        if os.path.isfile(os.path.join(target_path, "pom.xml")):
            return "maven"
        if any(
                os.path.isfile(os.path.join(target_path, f))
                for f in ("gradlew", "gradlew.bat", "build.gradle", "build.gradle.kts", "settings.gradle",
                          "settings.gradle.kts")
        ):
            return "gradle"
        return "plain"

    def get_default_build_dir_name(self):
        if self.build_system == "gradle":
            return os.path.join("build", "classes", "java", "main")
        return os.path.join("target", "classes")

    def ensure_project_ready(self):
        if self.build_system == "maven":
            return
        if self.build_system == "gradle":
            self.run_gradle_build()
            return
        self.compile_plain_project()

    def run_gradle_build(self):
        gradlew_bat = os.path.join(self.target_path, "gradlew.bat")
        gradlew = os.path.join(self.target_path, "gradlew")
        if os.path.isfile(gradlew_bat):
            cmd = [gradlew_bat, "classes", "--no-daemon"]
        elif os.path.isfile(gradlew):
            cmd = [gradlew, "classes", "--no-daemon"]
        else:
            cmd = ["gradle", "classes", "--no-daemon"]
        try:
            subprocess.run(cmd, cwd=self.target_path, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        except FileNotFoundError:
            return

    def get_gradle_module_dirs(self, project_path):
        module_dirs = []
        for d in os.listdir(project_path):
            candidate = os.path.join(project_path, d)
            if not os.path.isdir(candidate):
                continue
            if os.path.isfile(os.path.join(candidate, "build.gradle")) or os.path.isfile(
                    os.path.join(candidate, "build.gradle.kts")
            ):
                module_dirs.append(candidate)
        return module_dirs

    def get_gradle_main_runtime_classpath(self):
        init_script = None
        try:
            with tempfile.NamedTemporaryFile(delete=False, suffix=".gradle", mode="w", encoding="utf-8") as f:
                init_script = f.name
                f.write(
                    "allprojects { p ->\n"
                    "  p.tasks.register('printLogicCotMainRuntimeClasspath') {\n"
                    "    doLast {\n"
                    "      try {\n"
                    "        def ss = p.extensions.findByName('sourceSets')\n"
                    "        if (ss != null && ss.findByName('main') != null) {\n"
                    "          println(ss.main.runtimeClasspath.asPath)\n"
                    "        }\n"
                    "      } catch (Throwable ignored) {\n"
                    "      }\n"
                    "    }\n"
                    "  }\n"
                    "}\n"
                )

            gradlew_bat = os.path.join(self.target_path, "gradlew.bat")
            gradlew = os.path.join(self.target_path, "gradlew")
            if os.path.isfile(gradlew_bat):
                cmd = [gradlew_bat, "-I", init_script, "-q", "printLogicCotMainRuntimeClasspath", "--no-daemon"]
            elif os.path.isfile(gradlew):
                cmd = [gradlew, "-I", init_script, "-q", "printLogicCotMainRuntimeClasspath", "--no-daemon"]
            else:
                cmd = ["gradle", "-I", init_script, "-q", "printLogicCotMainRuntimeClasspath", "--no-daemon"]
            result = subprocess.run(cmd, cwd=self.target_path, stdout=subprocess.PIPE, stderr=subprocess.DEVNULL,
                                    text=True)
            cp_lines = [line.strip() for line in result.stdout.splitlines() if line.strip()]
            if not cp_lines:
                return ""
            parts = []
            for line in cp_lines:
                parts.extend([p for p in line.split(os.pathsep) if p])
            parts = list(dict.fromkeys(parts))
            return os.pathsep.join(parts)
        except Exception:
            return ""
        finally:
            if init_script and os.path.exists(init_script):
                try:
                    os.unlink(init_script)
                except Exception:
                    pass

    def compile_plain_project(self):
        output_dir = os.path.join(self.target_path, "target", "classes")
        os.makedirs(output_dir, exist_ok=True)
        sources = self.find_plain_java_sources()
        if not sources:
            return
        jars = self.find_local_jars()
        javac_exe = self.find_java_tool("javac")
        cmd = [javac_exe, "-d", output_dir]
        if jars:
            cmd.extend(["-cp", os.pathsep.join(jars)])
        cmd.extend(sources)
        subprocess.run(cmd, cwd=self.target_path, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

    def find_plain_java_sources(self):
        preferred = os.path.join(self.target_path, "src", "main", "java")
        sources = []
        if os.path.isdir(preferred):
            sources = glob.glob(os.path.join(preferred, "**", "*.java"), recursive=True)
        else:
            sources = glob.glob(os.path.join(self.target_path, "**", "*.java"), recursive=True)
        filtered = []
        for s in sources:
            norm = os.path.normpath(s)
            if f"{os.sep}src{os.sep}test{os.sep}" in norm:
                continue
            if f"{os.sep}test{os.sep}" in norm:
                continue
            if f"{os.sep}tests{os.sep}" in norm:
                continue
            filtered.append(s)
        return filtered

    def find_local_jars(self):
        jar_dirs = [
            os.path.join(self.target_path, "lib"),
            os.path.join(self.target_path, "libs"),
            os.path.join(self.target_path, "target", "dependency"),
            os.path.join(self.target_path, "build", "libs"),
        ]
        jars = []
        for d in jar_dirs:
            if os.path.isdir(d):
                jars.extend(glob.glob(os.path.join(d, "*.jar")))
        if not jars:
            jars = glob.glob(os.path.join(self.target_path, "**", "*.jar"), recursive=True)
        return list(dict.fromkeys(jars))

    def get_instrument_target_class_dirs(self):
        dirs = []
        for d in getattr(self, "class_dirs", []) or []:
            if os.path.isdir(d):
                dirs.append(d)

        if dirs:
            return list(dict.fromkeys(dirs))

        maven_default = os.path.join(self.target_path, "target", "classes")
        if os.path.isdir(maven_default):
            return [maven_default]

        gradle_default = os.path.join(self.target_path, "build", "classes", "java", "main")
        if os.path.isdir(gradle_default):
            return [gradle_default]

        return []

    def find_project_output_jars(self):
        jars = []
        jars.extend(glob.glob(os.path.join(self.target_path, "target", "*.jar")))
        jars.extend(glob.glob(os.path.join(self.target_path, "build", "libs", "*.jar")))
        jars.extend(glob.glob(os.path.join(self.target_path, "**", "target", "*.jar"), recursive=True))
        jars.extend(glob.glob(os.path.join(self.target_path, "**", "build", "libs", "*.jar"), recursive=True))
        return list(dict.fromkeys(jars))

    @staticmethod
    def write_text_file(path, content):
        os.makedirs(os.path.dirname(path), exist_ok=True)
        with open(path, "w", encoding="utf-8") as f:
            f.write(content or "")

    @staticmethod
    def to_posix_path(path):
        return str(path).replace("\\", "/")

    @staticmethod
    def to_bash_path(path):
        p = str(path)
        m = re.match(r"^([A-Za-z]):[\\\\/](.*)$", p)
        if m:
            drive = m.group(1).lower()
            rest = m.group(2).replace("\\", "/")
            return f"/{drive}/{rest}"
        return p.replace("\\", "/")

    def resolve_path(self, value):
        if not value:
            return value
        value_str = str(value).strip()
        if os.path.isabs(value_str):
            return value_str
        return os.path.normpath(os.path.join(self.config_base_dir, value_str))

    def resolve_classpath_value(self, value):
        if not value:
            return value
        parts = []
        for p in str(value).split(os.pathsep):
            p = p.strip()
            if not p:
                continue
            parts.append(self.resolve_path(p))
        return os.pathsep.join(parts)

    def get_cobertura_java_classpath(self):
        cobertura_dir = self.resolve_path(COBERTURA_DIR)
        jars = [os.path.join(cobertura_dir, "cobertura-2.1.1.jar")]
        lib_dir = os.path.join(cobertura_dir, "lib")
        if os.path.isdir(lib_dir):
            jars.extend(glob.glob(os.path.join(lib_dir, "*.jar")))
        filtered = []
        for j in jars:
            if not os.path.isfile(j):
                continue
            base = os.path.basename(j).lower()
            if base.startswith("junit-") or base.startswith("hamcrest-"):
                continue
            filtered.append(j)
        jars = filtered
        return os.pathsep.join(jars)

    def run_cobertura_instrument(self, instrument_dir, datafile_dir, target_class_dirs):
        java_exe = self.find_java_tool("java")
        cp = self.get_cobertura_java_classpath()
        destination_dir = os.path.join(instrument_dir, "instrumented")
        datafile_path = os.path.join(datafile_dir, "cobertura.ser")
        cmd = [
            java_exe,
            "-cp",
            cp,
            "net.sourceforge.cobertura.instrument.InstrumentMain",
            "--basedir",
            self.target_path,
            "--destination",
            destination_dir,
            "--datafile",
            datafile_path,
            *target_class_dirs,
        ]
        return subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    def run_cobertura_report(self, datafile_dir, report_dir):
        java_exe = self.find_java_tool("java")
        cp = self.get_cobertura_java_classpath()
        datafile_path = os.path.join(datafile_dir, "cobertura.ser")
        cmd = [
            java_exe,
            "-cp",
            cp,
            "net.sourceforge.cobertura.reporting.ReportMain",
            "--format",
            REPORT_FORMAT,
            "--datafile",
            datafile_path,
            "--destination",
            report_dir,
        ]
        return subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    @staticmethod
    def find_java_tool(tool_name):
        exe = shutil.which(tool_name)
        if exe:
            return exe
        java_home = os.environ.get("JAVA_HOME")
        if java_home:
            suffix = ".exe" if os.name == "nt" else ""
            candidate = os.path.join(java_home, "bin", tool_name + suffix)
            if os.path.isfile(candidate):
                return candidate
        return tool_name

    @staticmethod
    def normalize_classpath_entries(*values):
        entries = []
        for value in values:
            if not value:
                continue
            value_str = str(value)
            for part in value_str.split(os.pathsep):
                part = part.strip()
                if not part:
                    continue
                if os.pathsep == ";" and ":" in part:
                    subparts = re.split(r"(?i)(?<=\\.jar):", part)
                    for sp in subparts:
                        sp = sp.strip()
                        if sp:
                            entries.append(sp)
                else:
                    entries.append(part)
        return entries

    @staticmethod
    def detect_java_major():
        try:
            result = subprocess.run(["java", "-version"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            version_output = (result.stderr or "") + "\n" + (result.stdout or "")
            match = re.search(r'version\\s+\"([^\"]+)\"', version_output)
            if not match:
                match = re.search(r'openjdk\\s+version\\s+\"([^\"]+)\"', version_output)
            if not match:
                return 8
            raw = match.group(1).strip()
            if raw.startswith("1."):
                return int(raw.split(".", 2)[1])
            return int(raw.split(".", 1)[0])
        except Exception:
            return 8
