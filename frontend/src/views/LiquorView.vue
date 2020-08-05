<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat width="614" class="mx-auto">
          <h1 class="text-left" style="color: #ff6666;">금주의 칵테일</h1>
          <br />
          <div v-for="(liquor, n) in liquor_data" :key="n" class="container">
            <v-row class="ma-0">
              <!-- <v-col
                cols="1"
                style="background-color: #ffb3ff;"
                class="ma-auto px-0 py-1 white--text"
              >{{ liquor.id }}.</v-col>-->
              <v-tooltip bottom color="transparent">
                <template v-slot:activator="{ on, attrs }">
                  <v-col
                    cols="3"
                    style="background-color: #ffb3b3;"
                    class="ma-auto px-0 py-1 white--text"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <span>{{ liquor.liquor1 }}</span>
                  </v-col>
                </template>
                <img :src="liquor.liquor1img" width="100vw" />
              </v-tooltip>

              <v-col cols="1" class="ma-auto pa-0">
                <v-icon color="#ff6666">mdi-plus</v-icon>
              </v-col>

              <v-tooltip bottom color="transparent">
                <template v-slot:activator="{ on, attrs }">
                  <v-col
                    cols="3"
                    style="background-color: #ffb3b3;"
                    class="ma-auto px-0 py-1 white--text"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <span>{{ liquor.liquor2 }}</span>
                  </v-col>
                </template>
                <img :src="liquor.liquor2img" width="100vw" />
              </v-tooltip>

              <v-col cols="1" class="ma-auto pa-0">
                <v-icon color="#ff6666">mdi-equal</v-icon>
              </v-col>

              <v-tooltip bottom color="transparent">
                <template v-slot:activator="{ on, attrs }">
                  <v-col
                    cols="3"
                    style="background-color: #ffb3b3;"
                    class="ma-auto px-0 py-1 white--text"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <span>{{ liquor.liquor3 }}</span>
                  </v-col>
                </template>
                <img :src="liquor.liquor3img" width="100vw" />
              </v-tooltip>
            </v-row>
            <v-divider v-if="n+1!=liquor_data.length" color="#ff6666" class="my-3"></v-divider>
          </div>
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
        {
          liquor1: "에스프레소",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
        {
          liquor1: "커피",
          liquor1img:
            "https://imgsnacker.hankyung.com/wp-content/uploads/2016/08/coffee-540x360.jpg",
          liquor2: "소주",
          liquor2img:
            "https://file.mk.co.kr/meet/neds/2019/04/image_readtop_2019_261362_15561720623725165.jpg",
          liquor3: "깔루아",
          liquor3img: "https://t1.daumcdn.net/cfile/tistory/991192335A26B2FE02",
        },
      ],
    };
  },
};
</script>

<style scoped>
</style>