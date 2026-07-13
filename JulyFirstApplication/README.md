프로젝트 요약 및 학습 정리
===========================

체크리스트
- [x] 오늘 날짜(작업 기준) 추가: 2026-07-01
- [x] 각 모듈별 학습 내용 요약 정리
- [x] 실행/빌드 주의사항 및 확인해야 할 점 명시

개요
---
이 레포지토리는 Android 실습용 여러 모듈로 구성되어 있으며, 각 모듈은 특정 개념을 학습/실습하기 위한 예제들을 포함합니다. 아래는 2026-07-01 기준으로 각 모듈의 주요 학습 포인트와 실행 노트입니다.

모듈별 요약
---

1) app
- 주요 목적: 프로그래밍 방식으로 UI를 구성하는 예제(코드에서 View 생성 후 setContentView 사용)
- 주요 파일: app/src/main/java/.../MainActivity.kt, app/src/main/res/layout/activity_main.xml
- 학습 포인트: 런타임에 TextView/ImageView/LinearLayout을 생성하고 레이아웃을 구성하는 방법
- 주의: MainActivity에서 사용하는 R.drawable.cat 등의 drawable 리소스가 실제로 존재해야 정상 동작합니다.

2) myapplication
- 주요 목적: Edge-to-Edge 처리 및 WindowInsets 적용 실습
- 주요 파일: myapplication/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml
- 학습 포인트: enableEdgeToEdge() 사용, ViewCompat.setOnApplyWindowInsetsListener로 시스템 바 인셋 처리
- 주의: minSdk 및 컴파일 SDK 설정을 확인하여 호환성 문제를 방지하세요.

3) myapplication2
- 주요 목적: myapplication과 유사한 Edge-to-Edge/WindowInsets 실습(버전 차이로 구성 비교)
- 주요 파일: myapplication2/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml
- 학습 포인트: 모듈별 compileSdk/targetSdk 차이를 이해하고 조정하는 방법

4) todaylunch
- 주요 목적: 간단한 폼 UI(체크박스, 라디오그룹, 버튼)로 ‘오늘 점심’ 선택 예제
- 주요 파일: todaylunch/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml
- 학습 포인트: CheckBox/RadioButton/RadioGroup 처리, 버튼 클릭으로 사용자 입력 반영

5) viewattribute
- 주요 목적: MotionLayout/MotionScene과 레이아웃 속성 연동 실습
- 주요 파일: viewattribute/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml, res/xml/activity_main_scene.xml
- 학습 포인트: MotionScene과 layoutDescription으로 애니메이션 구조를 정의하는 방법
- 주의: activity_main_scene.xml에 ConstraintSet/Transition 정의가 충분히 되어 있는지 확인해야 실제 애니메이션이 동작합니다.

6) viewbindingexam
- 주요 목적: View Binding을 이용한 안전한 뷰 참조 및 이벤트 처리 실습
- 주요 파일: viewbindingexam/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml
- 학습 포인트: ActivityMainBinding.inflate(layoutInflater)로 바인딩 생성, binding.<viewId>로 뷰 접근
- 주의: 모듈의 build.gradle.kts에서 viewBinding이 활성화되어 있는지 확인하세요.

7) visibleclick
- 주요 목적: 버튼 클릭으로 뷰의 visibility(VISIBLE/INVISIBLE/GONE)를 제어하는 실습
- 주요 파일: visibleclick/src/main/java/.../MainActivity.kt, res/layout/activity_main.xml
- 학습 포인트: View.VISIBLE/INVISIBLE/GONE의 차이와 setOnClickListener로 상태 변경하기
- 주의: 코드 내 잘못된 import(androidx.activity.R 등)가 있는지 확인하고, 필요시 App의 R이나 android.view.View 상수를 사용하도록 수정하세요.

공통 실행/빌드 노트
- Android Studio에서 프로젝트를 열고, 실행하려는 모듈의 Run/Configuration을 선택하여 실행하세요. 각 모듈은 독립적으로 실행 가능한 Activity를 포함하고 있습니다.
- 일부 예제는 특정 리소스(drawable, strings 등)에 의존합니다. 에러 발생 시 res 폴더의 리소스가 존재하는지 확인하세요.
- MotionLayout 사용 모듈은 ConstraintLayout 및 관련 지원 라이브러리 버전이 맞지 않으면 빌드/런타임 문제가 발생할 수 있으니 build.gradle.kts의 의존성 버전을 확인하세요.

알려진 잠재적 이슈
- 일부 파일에서 잘못된 import가 발견될 수 있습니다(예: androidx.activity.R). 이런 경우 올바른 R 또는 android.view.View 상수로 수정해야 합니다.
- drawable 리소스 누락(예: app 모듈의 R.drawable.cat) 시 이미지 관련 코드에서 예외가 발생합니다.

권장 작업
- 각 모듈의 MainActivity와 layout 파일을 열어 주석을 읽고, 직접 변경/실습해보세요.
- 리소스가 없거나 import 오류가 발생하면 에러 메시지를 확인하여 필요한 리소스를 추가하거나 import를 정정하세요.

작성일: 2026-07-01

