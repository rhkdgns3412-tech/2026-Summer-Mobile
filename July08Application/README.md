# July08Application

## 오늘 날짜

- 2026-07-08

## 오늘 학습 내용 정리

오늘은 Android Studio에서 여러 개의 예제를 통해 **화면 전환, 결과 전달, 피커, 다이얼로그**를 학습했다.

### 1. 계산기 앱 (`app`)
- 두 개의 숫자를 입력받아 사칙연산을 수행하는 계산기 흐름을 구현했다.
- `ActivityResultLauncher`를 사용해서 `CalcActivity`로 이동한 뒤, 계산 결과를 다시 받아오는 구조를 익혔다.
- `Intent`의 `putExtra()`와 `getDoubleExtra()` / `getStringExtra()`를 활용해 데이터를 전달했다.
- 결과에 따라 안내 문구의 색상을 파란색/빨간색으로 다르게 표시했다.

### 2. 날짜/시간 피커 예제 (`picker`)
- `DatePicker`와 `TimePicker`를 화면에 직접 배치하고, 버튼으로 보이기/숨기기를 전환하는 방법을 학습했다.
- 각 피커를 선택적으로 보여주면서 UI 상태를 바꾸는 방식에 익숙해졌다.

### 3. 다이얼로그 예제 (`pickerdialog`)
- `DatePickerDialog`, `TimePickerDialog`를 사용해 팝업 형태의 날짜/시간 선택 기능을 구현했다.
- 커스텀 다이얼로그와 알림 다이얼로그를 띄우는 흐름도 함께 익혔다.
- 버튼 클릭 시 다이얼로그를 실행하고, 선택 결과를 버튼 텍스트에 반영하는 방법을 정리했다.

## 핵심 키워드
- `Intent`
- `ActivityResultLauncher`
- `DatePicker`
- `TimePicker`
- `DatePickerDialog`
- `TimePickerDialog`
- `CustomDialog`
- `CustomAlertDialog`

## 느낀 점

오늘 실습을 통해 Android에서 **화면 간 데이터 전달**과 **사용자 입력 UI 구성**이 어떻게 연결되는지 전체 흐름을 이해할 수 있었다.

