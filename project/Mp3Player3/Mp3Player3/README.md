<<<<<<< HEAD
# 2026-여름학기 모바일 프로그래밍 수업
## 학번: 20232878
## 이름 : 윤광훈
## 이메일: rhkdgns3412@gmail.com
--
=======
Mp3Player3
===========

한 줄: 이 문서는 현재 작업한 MP3 플레이어 앱의 기능 요약, 빌드/실행 방법, 수정된 파일 목록과 미구현 사항을 정리합니다.

체크리스트
- [x] 앱 주요 기능 요약 작성
- [x] 빌드 및 실행 방법 추가
- [x] 수정/추가된 주요 파일 목록 기재
- [x] 현재 구현 상태와 미구현(제약) 항목 명시

요약
----
Mp3Player3는 로컬 기기에 저장된 음악 파일을 재생하기 위한 간단한 안드로이드 앱입니다. 플레이리스트와 좋아요(Favorites) 기능을 지원하며, 별도의 `PlayerActivity`에서 재생을 담당합니다.

핵심 기능(현재 구현)
- 로컬 음악 라이브러리에서 곡 목록 로드
- 플레이리스트 생성 및 플레이리스트에 곡 추가 (`PlaylistFragment`)
- 좋아요(즐겨찾기) 기능: `FavoritesRepository`에 즐겨찾기 곡을 추가/제거
- `FavoriteFragment`에서 좋아요 목록 조회 및 재생
- 재생 컨트롤 (PlayerActivity): 재생/일시정지, 이전/다음, 시크바
- 셔플(Shuffle) 버튼: 다음 곡 선택 시 현재 재생목록에서 랜덤 인덱스 선택
- 반복(Repeat) 버튼: "현재 곡 반복" 토글 (활성화하면 곡이 끝날 때 다시 같은 곡을 처음부터 재생)
- 플레이리스트 단위 재생: 플레이리스트의 곡들만 PlayerActivity에서 재생

재생 흐름(요약)
- 기본: 한 곡이 끝나면 다음 곡을 재생합니다. 재생목록 끝에 도달하면 재생을 멈춥니다.
- Shuffle ON: 다음 곡 선택을 현재 재생목록에서 랜덤으로 결정합니다 (현재는 중복 가능).
- Repeat ON: 곡이 끝났을 때 다음곡으로 넘어가지 않고 현재 곡을 처음부터 다시 재생합니다.

수정/추가된 주요 파일
- `app/src/main/java/com/example/mp3player3/ui/PlayerActivity.kt` — 재생 흐름(다음/이전/완료 시 행동), 셔플/반복 토글, 좋아요 버튼 연동
- `app/src/main/java/com/example/mp3player3/player/MusicPlayer.kt` — MediaPlayer 래퍼; 곡 완료 후 재개 동작 보강
- `app/src/main/java/com/example/mp3player3/repository/FavoritesRepository.kt` — 즐겨찾기 관리(이미 존재)
- Fragment들: `PlaylistFragment.kt`, `FavoriteFragment.kt` (플레이리스트/좋아요 UI 및 PlayerActivity 호출)
- 모델: `model/Song.kt`, `model/Playlist.kt` (Serializable로 Intent에 전달 가능)

빌드 · 실행
1. Android Studio에서 프로젝트 열기
2. 필요한 권한(READ_EXTERNAL_STORAGE 등)을 확인하고, 에뮬레이터나 실제 기기에 음악 파일이 있어야 함
3. 디버그 빌드:

```powershell
cd "C:\Users\yoon_kwanghoon\AndroidStudioProjects\Mp3Player3"
.\gradlew.bat assembleDebug
```

4. Android Studio에서 앱 실행 또는 다음을 통해 설치 & 실행:

```powershell
.\gradlew.bat installDebug
```

동작 확인 가이드
- 앱을 실행하고 "Playlist" 또는 "Song" 탭에서 곡을 선택합니다.
- 플레이리스트를 선택 후 곡을 클릭하면 `PlayerActivity`가 열리며 해당 플레이리스트 곡만 재생됩니다.
- PlayerActivity에서 셔플/반복 버튼을 눌러 동작을 확인하세요.

제약 및 미구현 사항
1. 앨범, 아티스트 부분은 androidstudio에서 앨범과 아티스트가 안읽히는 문제때문에 구현불가
   - 설명: 현재 개발환경(또는 테스트 기기)의 미디어 스캐닝/권한 문제로 앨범/아티스트 메타데이터가 안정적으로 읽히지 않았습니다. 이로 인해 앨범 또는 아티스트 기반의 뷰(그룹화/정렬)를 구현하지 못했습니다.
   - 권장 해결방법: 실제 기기에서 미디어 스캐닝을 확인하고, 필요한 권한을 요청한 뒤 MediaStore 쿼리를 통해 메타데이터를 재시도하거나, 앱 초기화 시 MediaScannerConnection을 사용해 재스캔을 유도하세요.


