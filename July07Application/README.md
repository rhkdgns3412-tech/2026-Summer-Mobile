# July07Application 학습 정리

- 날짜: 2026-07-07
- 프로젝트: `July07Application`

## 오늘 학습한 내용

### 1. `app` 디렉토리 확인
- `app`은 안드로이드 앱의 핵심 모듈로, 실제 기능과 화면 관련 파일이 모여 있는 곳이다.
- `app/build.gradle.kts`에서 앱의 의존성, 컴파일 옵션, 플러그인 설정을 확인할 수 있다.
- `app/src/main` 아래에는 보통 `AndroidManifest.xml`, Kotlin/Java 소스, 리소스 파일이 위치한다.

### 2. `res/layout` 디렉토리 확인
- `res/layout`에는 화면 UI를 구성하는 XML 레이아웃 파일이 들어간다.
- 각 레이아웃 파일은 버튼, 텍스트뷰, 이미지뷰 같은 화면 구성 요소를 배치하는 역할을 한다.
- 화면 디자인을 변경할 때는 이 디렉토리의 XML 파일을 수정하면 된다.

### 3. Android 프로젝트 구조 이해
- 루트에는 `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties` 같은 Gradle 설정 파일이 있다.
- `app/`는 앱 모듈, `res/`는 앱의 리소스를 담는 공간으로 구분해서 이해할 수 있다.
- 빌드 과정에서 생성되는 파일은 `build/`와 `app/build/`에 저장된다.

### 4. 정리한 내용
- 오늘은 `app` 디렉토리와 `res/layout` 디렉토리를 직접 확인하면서 프로젝트 구조를 익혔다.
- 특히 화면 구성은 `res/layout`의 XML 파일로 관리된다는 점을 학습했다.

### 5. 여러 Activity와 Intent로 데이터 주고받기 (오늘 학습한 핵심)
- 이 프로젝트에는 `MainActivity`, `SecondActivity`, `ThirdActivity`, `FourthActivity` 네 개의 Activity가 있다.
- Activity 간 전환은 명시적 Intent를 사용해 이루어진다. 예: `MainActivity`에서 `SecondActivity`로 이동할 때 Intent에 `putExtra("Where", value)`처럼 데이터를 실어 보낸다.
- 수신 Activity에서는 `intent.getStringExtra("Where")` 또는 `intent.getIntExtra("Value", 0)`로 전달된 값을 읽는다.
- Activity 선언은 `app/src/main/AndroidManifest.xml`에 등록되어 있으며, `MainActivity`가 LAUNCHER로 설정되어 있다.
- 레이아웃 파일(`app/src/main/res/layout/*.xml`)에는 각 Activity에서 사용하는 버튼들이 정의되어 있고, 버튼 id(`secondBtn`, `thirdBtn`, `fourthBtn`, `finish2nd` 등)를 통해 클릭 이벤트에서 Intent를 생성해 화면을 전환한다.
- 이 프로젝트에서는 `startActivityForResult`/`onActivityResult`나 `ActivityResultLauncher` 기반의 API는 사용하지 않고, 단방향 Intent 전달(putExtra → getExtra) 패턴을 사용하고 있다.

## 마무리
오늘 학습을 통해 Android 프로젝트에서 `app`과 `res/layout`의 역할을 이해했다.
다음에는 `src/main/java` 또는 `src/main/kotlin`의 코드와 레이아웃 연결 방식을 더 살펴볼 예정이다.
