# July09Application

작성일: 2026-07-09

## 목적
이 저장소는 Android 실습용 멀티모듈 프로젝트로, 각 모듈을 통해 Fragment, RecyclerView, ViewPager2, DrawerLayout 등 Android UI 구성요소와 ViewBinding 사용법을 학습하기 위해 구성되어 있습니다.

## 요약 및 학습 내용
아래 내용은 프로젝트 내 각 모듈의 `build.gradle.kts`를 참조하여 정리했습니다. 공통적으로 각 모듈은 ViewBinding이 활성화되어 있고, Java 11을 사용하도록 설정되어 있습니다. compileSdk는 37, minSdk는 29, targetSdk는 37로 설정되어 있습니다.

- 공통 사항
  - ViewBinding: `viewBinding.enable = true`로 모든 앱 모듈에서 사용 가능
  - Java 버전: source/target compatibility = Java 11
  - SDK: compileSdk 37, minSdk 29, targetSdk 37

- `:app` (namespace: kr.hnu.ai.july09application)
  - 기본 메인 애플리케이션 모듈입니다.
  - core AndroidX 라이브러리들( activity-ktx, appcompat, constraintlayout, core-ktx, material )을 사용합니다.
  - 테스트 의존성으로 `junit`, Android 계정 테스트(`androidx.espresso`, `androidx.junit`) 포함
  - 실습에서 전체 앱 구조와 ViewBinding 적용, 레이아웃 제약 배치(ConstraintLayout) 사용을 학습할 수 있습니다.

- `:fragmentapp` (namespace: kr.hnu.ai.fragmentapp)
  - Fragment 개념을 중심으로 한 실습용 모듈입니다.
  - `androidx.fragment` 의존성을 포함하여 Fragment 생명주기와 fragment 관련 API를 학습하기 좋습니다.
  - ViewBinding과 Fragment 연동 예제 구현에 적합합니다.

- `:recylerview` (namespace: kr.hnu.ai.recylerview)
  - RecyclerView 사용법을 실습하는 모듈입니다.
  - `androidx.recyclerview` 의존성을 포함하고 있어 Adapter, ViewHolder, 레이아웃 매니저, 항목 클릭 처리 등을 학습할 수 있습니다.

- `:viewpager` (namespace: kr.hnu.ai.viewpager)
  - ViewPager2 기반 페이지 전환 및 페이저 어댑터 사용법을 실습하는 모듈입니다.
  - `androidx.viewpager2` 의존성을 포함합니다.
  - Fragment와 함께 사용하는 페이저 구성(예: FragmentStateAdapter) 등을 다루기 적합합니다.

- `:mydrawer` (namespace: kr.hnu.ai.mydrawer)
  - DrawerLayout(네비게이션 드로어)을 다루는 실습 모듈입니다.
  - `androidx.drawerlayout:drawerlayout:1.2.0` 의존성을 명시적으로 포함하고 있습니다.
  - 네비게이션 드로어 열기/닫기, 메뉴 처리 및 DrawerLayout과 Toolbar 연동 등을 학습할 수 있습니다.

## 참고 파일
- 각 모듈의 설정: `app/build.gradle.kts`, `fragmentapp/build.gradle.kts`, `recylerview/build.gradle.kts`, `viewpager/build.gradle.kts`, `mydrawer/build.gradle.kts`
- 멀티모듈 설정: `settings.gradle.kts` (모듈 포함 목록 확인)

## 빌드 및 실행
로컬에서 빌드/실행하려면 Android Studio로 프로젝트를 열어 각 모듈의 실행 구성을 설정하거나, 명령줄에서 Gradle wrapper를 사용하세요.

Windows PowerShell 예시:

```
# 전체 빌드
./gradlew.bat assembleDebug;

# 특정 모듈만 빌드 (예: app)
./gradlew.bat :app:assembleDebug;
```

## 학습 팁(추천 순서)
1. `:app` 모듈로 프로젝트 구조와 ViewBinding 사용법 숙지
2. `:fragmentapp`로 Fragment 생명주기와 ViewBinding-Fragment 연동 학습
3. `:recylerview`로 리스트 처리 패턴(Adapter, ViewHolder) 실습
4. `:viewpager`로 페이지 전환과 페이저 어댑터 학습
5. `:mydrawer`로 DrawerLayout 및 네비게이션 드로어 구현 연습

---
원하시면 README를 영어로 번역하거나, 각 모듈별 상세 사용법(예: 주요 Activity/Fragment 파일 목록, 예제 코드 스니펫)을 추가해 드리겠습니다.
