<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat width="614" class="mx-auto">
          <h1 class="text-left" style="color: #ff6666;">금주의 칵테일</h1>
          <br />
          <v-row class="ma-0 align-center justify-center">
            <v-col
              v-for="(liquor, n) in liquor_data"
              :key="n"
              :cols="n % 4 == 0 ? 8 : n % 4 == 1 ? 1 : n % 4 == 2 ? 3 : 12"
              class="white--text text-center"
            >
              <v-card
                flat
                :color="!item.operation ? '#ffb3b3' : ''"
                :class="
                  !item.operation
                    ? 'mb-1 pa-1 d-inline-flex white--text'
                    : 'ma-0 pa-0 d-inline-flex justify-center align-center'
                "
                v-for="(item, i) in liquor"
                style="vertical-align: middle;"
                :key="i"
              >
                <v-tooltip
                  v-if="!item.operation"
                  bottom
                  transition="scroll-y-transition"
                  color="transparent"
                  class="align-center justify-center text-center"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <img
                      :src="item.liquorimg"
                      v-bind="attrs"
                      v-on="on"
                      width="40"
                      height="40"
                      class="liquor-image"
                    />
                  </template>
                  <img :src="item.liquorimg" width="325" />
                  <br />
                  <strong>
                    <h3
                      :style="
                        !item.operation ? 'background-color: #ffb3b3;' : ''
                      "
                      class="pa-1 text-center"
                      v-html="item.liquor"
                    ></h3>
                  </strong>
                </v-tooltip>
                <v-icon class="operator" v-else color="#ff6666">{{
                  item.operation
                }}</v-icon>
              </v-card>
              <v-divider
                class="my-3"
                color="#ff6666"
                light
                v-if="n % 4 == 3"
              ></v-divider>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "LiquorView",
  data() {
    return {
      liquor_data: [
        [
          {
            liquor: "밀키스",
            liquorimg: require("@/assets/liquor/milkis.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "블루 레몬에이드",
            liquorimg: require("@/assets/liquor/blueL.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "밀키스 블루<br/>밀키스 + 블루레몬에이드 + (얼음)<br/>/ 1 : 1 /<br/>소주 2oz or 보드가 1oz",
            liquorimg: require("@/assets/liquor/milkisblue.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "모구모구 (or 코코팜)",
            liquorimg: require("@/assets/liquor/mogu.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "스파클링 (복숭아)",
            liquorimg: require("@/assets/liquor/sparkling.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "모구모구 스파클링<br/>모구모구(or 코코팜) + 스파클링(복숭아)<br/>/ 1 : 1 /<br/>소주 2oz or 보드가 1oz",
            liquorimg: require("@/assets/liquor/pinkC.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "모구모구 (or 코코팜)",
            liquorimg: require("@/assets/liquor/mogu.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "유어스 자몽 (or 오션스프레이)",
            liquorimg: require("@/assets/liquor/zamong.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "복숭아 자몽에이드<br/>모구모구(or 코코팜) + 유어스 자몽<br/>/ 1 : 1 /<br/>소주 1oz or 보드가 0.5oz",
            liquorimg: require("@/assets/liquor/peachzamong.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "갈아만든 배",
            liquorimg: require("@/assets/liquor/pear.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "갈아만든 유자",
            liquorimg: require("@/assets/liquor/citron.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "갈아만든 숙취음료<br/>갈아만든 배 + 갈아만든 유자<br/>/ 1 :1 /<br/>(숙취음료임 술 ㄴㄴ)",
            liquorimg: require("@/assets/liquor/citronpear.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "토닉워터",
            liquorimg: require("@/assets/liquor/tonic.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "슈웹스 (or 오렌지나)",
            liquorimg: require("@/assets/liquor/schweppes.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카, 진)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "레몬토닉<br/>토닉워터 + 슈웹스(or 오렌지나)<br/>/ 1 : 1 /<br/>소주 2oz or 보드가 1oz or 진 1oz",
            liquorimg: require("@/assets/liquor/lemontonic.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "소주",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "레몬토닉",
            liquorimg: require("@/assets/liquor/lemontonic.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor: "쏘토닉<br/>레몬토닉<br/>/ /2 : 1/<br/>소주",
            liquorimg: require("@/assets/liquor/sotonic.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "메로나",
            liquorimg: require("@/assets/liquor/merona.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "사이다",
            liquorimg: require("@/assets/liquor/cider.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "메로나 크림 소다<br/>메로나 1개 + 사이다 가득<br/>소주 2oz or 보드카 1oz",
            liquorimg: require("@/assets/liquor/meronacream.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "폴라포",
            liquorimg: require("@/assets/liquor/polapo.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "슈웹스",
            liquorimg: require("@/assets/liquor/schweppes.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "폴라포 소다<br/>폴라포 1개 + 슈웹스<br/>/ 1 : 2 /<br/>소주 2oz or 보드가 1oz",
            liquorimg: require("@/assets/liquor/polapoju.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "스크류바",
            liquorimg: require("@/assets/liquor/screw.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "사이다",
            liquorimg: require("@/assets/liquor/cider.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "스크류 소다<br/>스크류바 1개 + 사이다 가득<br/>소주 2oz or 보드카 1oz",
            liquorimg: require("@/assets/liquor/screwju.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "아이스초코",
            liquorimg: require("@/assets/liquor/icechoco.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "바나나우유",
            liquorimg: require("@/assets/liquor/banana.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "위스키 (보드카, 소주)",
            liquorimg: require("@/assets/liquor/wiski.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "초코 바나나<br/>아이스초코 + 바나나 우유<br/>/ 1 : 1 /<br/>소주 2oz or 보드카 1oz or 위스키 1oz",
            liquorimg: require("@/assets/liquor/chocobanana.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "녹차라떼",
            liquorimg: require("@/assets/liquor/green.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "초코우유",
            liquorimg: require("@/assets/liquor/choco.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "위스키, (보드카, 소주)",
            liquorimg: require("@/assets/liquor/wiski.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "초코녹차라떼<br/>녹차라떼 + 초코 우유<br/>/ 7 : 3 /<br/>소주 2oz or 보드카 1oz or 위스키 1oz",
            liquorimg: require("@/assets/liquor/greenchoco.jpg"),
          },
        ],
        [],
        [
          {
            liquor: "딸기우유",
            liquorimg: require("@/assets/liquor/strawberry.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "수박에이드",
            liquorimg: require("@/assets/liquor/watermelon.jpg"),
          },
          {
            operation: "mdi-plus",
          },
          {
            liquor: "소주 (보드카)",
            liquorimg: require("@/assets/liquor/soju.jpg"),
          },
        ],
        [
          {
            operation: "mdi-equal",
          },
        ],
        [
          {
            liquor:
              "딸박<br/>딸기우유 + 수박에이드<br/>/ 1 : 1 /<br/>소주 2oz or 보드카 1oz",
            liquorimg: require("@/assets/liquor/strawberrywatermelon.jpg"),
          },
        ],
      ],
    };
  },
  mounted() {
    this.$emit("change-page", 6);
  },
};
</script>

<style scoped>
@media (min-width: 992px) {
  .liquor-image {
    width: 100px !important;
    height: 100px !important;
  }
}
</style>
