# BadgeMeal Backend

## Gradle Guides

- Clean
```shell
./gradlew clean
```

- Make Jar
```shell
./gradlew bootJar
```

## API Docs  

#### 메뉴 API  

- **GET** `/api/menus`
  - 메뉴 리스트 조회
- **PUT** `/api/menu` 
  - 메뉴 수정
- **POST** `/api/menu` 
  - 메뉴 저장 
  - IPFS로 이미지를 업로드 합니다.
- **POST** `/api/menus`
  - 메뉴 리스트를 저장합니다.

#### 메뉴 인증 API

- **POST** `/api/verify/receipt`
  - 영수증 사진을 업로드해 랜덤 뽑기 결과를 인증합니다.

#### 랜덤 뽑기 API

- **GET** `/api/draw/count`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 횟수를 조회합니다.
- **PUT** `/api/draw/count`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 횟수를 수정합니다.
- **GET** `/api/draw/menuNo`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 후 매핑된 메뉴 번호를 조회합니다.
- **GET** `/api/draw/result`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 결과의 인증 여부를 조회합니다.
- **POST** `/api/draw/result`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 결과의 인증 여부를 저장합니다.
- **GET** `/api/draw/result/init`
  - 파라미터 주소의 오늘 회차 랜덤 뽑기 결과를 초기화합니다.

#### NFT API

- **GET** `/api/mintData`
  - 파라미터 주소에 매핑된 발행 데이터를 조회합니다.
- **POST** `/api/mintData`
  - 파라미터 주소에 매핑할 발행 데이터를 저장합니다.
- **GET** `/api/mintData/init`
  - 파라미터 주소에 매핑된 발행 데이터를 초기화합니다.
- **GET** `/api/nft/mintCount`
  - 파라미터 주소에 매핑된 현재 회차 NFT 발급 횟수를 조회합니다.
- **PUT** `/api/nft/mintCount`
  - 파라미터 주소에 매핑된 현재 회차 NFT 발급 횟수를 수정합니다.

#### MasterNFT API

- **GET** `/ipfs/getMasterNftMetadata`
  - 파라미터 메뉴 번호에 해당하는 MasterNFT의 메타 데이터를 조회합니다.
- **POST** `/ipfs/uploadMasterNftMetadata`
  - 파라미터 메뉴 번호에 매핑되는 MasterNFT의 이미지를 IPFS에 업로드합니다.
  - 추가로 title, description, imageUrl(IPFS)을 메타데이터로 업로드 합니다.

#### Vote API (해커톤 시연용)

- **GET** `/vote/endVote`
  - 투표를 종료하고 NFT 소유자들이 메뉴 제안을 시작합니다.
- **GET** `/vote/voteStart`
  - 투표를 시작합니다.

### Additional Links

* [Swagger UI](http://tostit.i234.me:5005/swagger-ui/index.html)

