# Human Evaluation Protocol

This document describes the design, criteria, and questionnaire used in the human evaluation of automatically generated unit tests.

---

## 1. Evaluation Dimensions

Human evaluators assessed each generated test case along three dimensions using a 5-point Likert scale.

### 1.1 Intent Clarity

**Definition**  
The extent to which the test code clearly conveys what functionality or scenario is being tested and why the test exists, without requiring inspection of the production code.

---

### 1.2 Logical Traceability

**Definition**  
The extent to which a developer can trace a test case back to the underlying logical conditions, execution paths, or decision points of the target method.

---

### 1.3 Maintainability

**Definition**  
The extent to which the test code is easy to modify, extend, or debug when the tested logic changes, including the willingness of a developer to maintain the test in practice.

---

## 2. Questionnaire Design

### Instructions to Participants

Participants were presented with a set of automatically generated unit tests.  
Each test case was produced by a different test generation approach, with the identity of the approach hidden from participants.

The order of test cases and generation approaches was randomized.  
Participants evaluated each test independently according to the questions below.

All questions were answered using a **5-point Likert scale**, where:

- **1 = Very Poor**
- **5 = Excellent**

---

## 3. Evaluation Questions

### Q1. Intent Clarity

**Question**  
How clearly does this test case communicate its testing intent?

| Score | Description                                                  |
| ----: | ------------------------------------------------------------ |
|     1 | The purpose of the test is unclear; requires significant effort to infer intent. |
|     2 | The intent is partially visible but ambiguous or incomplete. |
|     3 | The general purpose can be understood with moderate effort.  |
|     4 | The intent is clear and mostly self-explanatory.             |
|     5 | The intent is immediately clear and explicitly documented.   |

---

### Q2. Logical Traceability

**Question**  
How easily can you trace this test case to the underlying logic or execution paths of the tested method?

| Score | Description                                                  |
| ----: | ------------------------------------------------------------ |
|     1 | No clear relation between the test and the code logic.       |
|     2 | Some relation exists, but logical conditions are implicit or unclear. |
|     3 | Logical coverage can be inferred with effort.                |
|     4 | Logical conditions and paths are mostly explicit.            |
|     5 | Logical conditions and execution paths are explicitly and systematically documented. |

---

### Q3. Maintainability

**Question**  
If the tested method changes, how confident are you that you could efficiently maintain or update this test case?

| Score | Description                                                  |
| ----: | ------------------------------------------------------------ |
|     1 | Very difficult to maintain; would likely rewrite the test.   |
|     2 | Maintenance is possible but costly and error-prone.          |
|     3 | Moderate maintenance effort required.                        |
|     4 | Easy to maintain with minimal effort.                        |
|     5 | Very easy to maintain; test structure and intent are explicit. |

---

## 4. Scoring Procedure

For each test case, participants rated three dimensions:

- Intent Clarity
- Logical Traceability
- Maintainability

The final score of each dimension was computed as the **average rating across all participants**.

An **overall human evaluation score** was calculated as the mean of the three dimension scores.

---

## 5. Participants

All participants had prior experience with software development and were familiar with unit testing practices.

---

## 6. Notes

- All evaluations were conducted under a blind setting.
- Participants were not informed of the test generation approach.
- The evaluation focuses on **relative and qualitative comparison** between different approaches rather than absolute judgment.