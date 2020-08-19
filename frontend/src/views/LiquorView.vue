<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat width="614" class="mx-auto">
          <h1 class="text-left ml-3 mb-n3" style="color: #424242;">
            이번주의 칵테일
          </h1>
          <br />
          <v-row class="ma-0 align-center justify-center">
            <v-col
              cols="6"
              v-for="(liquor, n) in liquor_data"
              :key="n"
              @click="modal(liquor)"
            >
              <!-- 모달 방식 -->
              <v-dialog v-model="liquor.datum2.dialog" width="295">
                <template v-slot:activator="{ on, attrs }">
                  <v-card
                    v-bind="attrs"
                    v-on="on"
                    max-width="295px"
                    class="mx-auto"
                  >
                    <img
                      class="liquor-image"
                      :src="liquor.datum2.liquorimg"
                      width="100%"
                    />
                    <br />
                    <strong>
                      <h4
                        style="background-color: #fbc687; color: #424242;"
                        class="pa-1 text-center"
                        v-html="liquor.datum2.liquor"
                      ></h4>
                    </strong>
                  </v-card>
                </template>
                <v-card max-width="295px" class="mx-auto">
                  <Liquor :datum="liquor.datum1" />
                </v-card>
              </v-dialog>
              <!-- 툴팁 방식 -->
              <!-- <v-tooltip>
                <template v-slot:activator="{ on, attrs }">
                  <v-card
                    v-bind="attrs"
                    v-on="on"
                    max-width="295px"
                    class="mx-auto"
                  >
                    <img
                      class="liquor-image"
                      :src="liquor.datum2.liquorimg"
                      width="100%"
                    />
                    <br />
                    <strong>
                      <h3
                        style="background-color: #ffb3b3;"
                        class="pa-1 text-center"
                        v-html="liquor.datum2.liquor"
                      ></h3>
                    </strong>
                  </v-card>
                </template>
                <v-card max-width="295px" class="mx-auto">
                  <Liquor :datum="liquor.datum1" />
                </v-card>
              </v-tooltip>-->
            </v-col>
            <!-- <v-col cols="12"></v-col>
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
                <v-icon class="operator" v-else color="#ff6666">
                  {{ item.operation }}
                </v-icon>
              </v-card>
              <v-divider
                class="my-3"
                color="#ff6666"
                light
                v-if="n % 4 == 3"
              ></v-divider>
            </v-col>-->
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Liquor from "@/components/Liquor";

export default {
  name: "LiquorView",
  components: {
    Liquor,
  },
  data() {
    return {
      liquor_data: [
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "밀키스",
                backgroundColor: "#C7B48E",
                data: [30],
              },
              {
                label: "블루 레모네이드",
                backgroundColor: "#6E7096",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [40],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "밀키스 블루",
            liquorimg: require("@/assets/liquor/milkisblue.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "모구모구",
                backgroundColor: "#ff7171",
                data: [30],
              },
              {
                label: "스파클링",
                backgroundColor: "#6e6d6d",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [40],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "모구모구 스파클링",
            liquorimg: require("@/assets/liquor/pinkC.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "모구모구",
                backgroundColor: "#ff7171",
                data: [35],
              },
              {
                label: "유어스 자몽",
                backgroundColor: "#ea5455",
                data: [35],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "복숭아 자몽에이드",
            liquorimg: require("@/assets/liquor/peachzamong.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "갈아만든 배",
                backgroundColor: "#d8ba8a",
                data: [50],
              },
              {
                label: "갈아만든 유자",
                backgroundColor: "#EEC439",
                data: [50],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "갈아만든 숙취음료",
            liquorimg: require("@/assets/liquor/citronpear.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "토닉워터",
                backgroundColor: "#68b0ab",
                data: [40],
              },
              {
                label: "슈웹스",
                backgroundColor: "#8fc0a9",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "레몬토닉",
            liquorimg: require("@/assets/liquor/lemontonic.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "쏘토닉",
                backgroundColor: "#ddb34e",
                data: [70],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "쏘토닉",
            liquorimg: require("@/assets/liquor/sotonic.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "메로나",
                backgroundColor: "#ade498",
                data: [40],
              },
              {
                label: "사이다",
                backgroundColor: "#7fdbda",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "메로나 크림 소다",
            liquorimg: require("@/assets/liquor/meronacream.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "폴라포",
                backgroundColor: "#440047",
                data: [30],
              },
              {
                label: "슈웹스",
                backgroundColor: "#8fc0a9",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [40],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "폴라포 소다",
            liquorimg: require("@/assets/liquor/polapoju.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "스크류바",
                backgroundColor: "#e84a5f",
                data: [30],
              },
              {
                label: "사이다",
                backgroundColor: "#7fdbda",
                data: [30],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [40],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "스크류 소다",
            liquorimg: require("@/assets/liquor/screwju.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "아이스 초코",
                backgroundColor: "#411f1f",
                data: [40],
              },
              {
                label: "바나나 우유",
                backgroundColor: "#deb34e",
                data: [30],
              },
              {
                label: "위스키",
                backgroundColor: "#56556e",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "초코 바나나",
            liquorimg: require("@/assets/liquor/chocobanana.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "녹차라떼",
                backgroundColor: "#799351",
                data: [50],
              },
              {
                label: "초코우유",
                backgroundColor: "#411f1f",
                data: [20],
              },
              {
                label: "위스키",
                backgroundColor: "#56556e",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "초코녹차라떼",
            liquorimg: require("@/assets/liquor/greenchoco.jpg"),
            dialog: false,
          },
        },
        {
          datum1: {
            labels: [""],
            datasets: [
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
              {
                label: "딸기우유",
                backgroundColor: "#d54062",
                data: [35],
              },
              {
                label: "수박에이드",
                backgroundColor: "#206a5d",
                data: [35],
              },
              {
                label: "소주",
                backgroundColor: "#519872",
                data: [30],
              },
              {
                label: "",
                backgroundColor: "transparent",
                data: [10],
              },
            ],
          },
          datum2: {
            liquor: "딸박",
            liquorimg: require("@/assets/liquor/strawberrywatermelon.jpg"),
            dialog: false,
          },
        },
      ],
    };
  },
  mounted() {
    this.$emit("change-page", 5);
  },
};
</script>

<style scoped>
/* @media (min-width: 992px) {
  .liquor-image {
    width: 307 !important;
    height: 307 !important;
  }
} */
</style>
