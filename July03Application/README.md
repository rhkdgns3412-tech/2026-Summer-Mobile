# July 03 Application - Chronometer (스톱워치)

**작성일**: 2026년 7월 6일

## 📱 프로젝트 소개

Android의 **Chronometer 위젯**을 사용하여 스톱워치 기능을 구현한 애플리케이션입니다.
시작, 중지, 리셋 기능을 갖춘 기본적인 시간 측정 앱입니다.

## 🎯 학습한 내용

### 1. **Chronometer 위젯**
- Android에서 제공하는 시간 측정 위젯
- `start()`, `stop()` 메서드로 시간 측정 제어
- `base` 속성으로 시작 시점 설정

### 2. **SystemClock.elapsedRealtime()**
```kotlin
SystemClock.elapsedRealtime()
```
- 부팅 이후 경과한 실시간(밀리초 단위)
- 절전 상태도 포함하여 계산되는 시간
- Chronometer의 기준 시간으로 사용

### 3. **시간 상태 저장 및 복원**
```kotlin
var elapsedTime = 0L
```
- 중지된 시점의 경과 시간 저장
- 다시 시작할 때 이전 시간부터 계속할 수 있도록 구현

### 4. **버튼 상태 관리**
- `setOnClickListener`: 버튼 클릭 이벤트 처리
- 버튼 활성화/비활성화(`isEnabled`) 제어
- 상태에 따른 적절한 버튼 활성화

### 5. **Toast 메시지**
```kotlin
Toast.makeText(this@MainActivity, "메시지", Toast.LENGTH_SHORT).show()
```
- 사용자 피드백 제공
- 짧은 시간 동안 화면에 메시지 표시

### 6. **Data Binding**
```kotlin
val binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
```
- View 요소에 직접 접근 가능
- null-safety 제공
- 보일러플레이트 코드 감소

## 📋 주요 기능

### Start (시작)
- Chronometer 시작
- 이전의 경과 시간을 기반으로 계속 측정
- Start 버튼 비활성화, Stop 버튼 활성화

### Stop (중지)
- 현재 경과 시간 저장
- Chronometer 일시 중지
- Start 버튼 활성화, Stop 버튼 비활성화

### Reset (리셋)
- 경과 시간 초기화 (0L)
- Chronometer를 초기 상태로 복원
- 모든 버튼 활성화

## 💡 주요 코드 로직

```kotlin
// Start 로직: 이전 시간 + 현재 시간을 기준으로 설정
binding.chronometer.setBase(SystemClock.elapsedRealtime() + elapsedTime)

// Stop 로직: 현재 base - 현재 경과 시간 = 지난 시간
elapsedTime = binding.chronometer.base - SystemClock.elapsedRealtime()

// Reset 로직: 모든 변수 초기화
binding.chronometer.base = SystemClock.elapsedRealtime()
elapsedTime = 0L
```

## 🔧 기술 스택

- **Language**: Kotlin
- **Framework**: Android (AppCompat)
- **UI**: Data Binding
- **Widgets**: Chronometer, Button, Toast
- **Build**: Gradle (Kotlin DSL)

## 📚 학습 요점 정리

1. ✅ Chronometer의 기본 사용법 이해
2. ✅ 시간 계산 및 상태 관리 방법 학습
3. ✅ 버튼 이벤트 처리 및 UI 상태 제어
4. ✅ Data Binding을 활용한 View 접근
5. ✅ 안드로이드 생명주기와 상태 보존의 중요성 인식

