<template>
  <v-container fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card max-width="614" class="mx-auto" flat>
          <v-text-field
            v-model="keyword"
            append-icon="mdi-magnify"
            @input="delaySearch()"
            @click:append="movetoSearched()"
            @keyup.enter="movetoSearched()"
            label="검색어를 입력하세요"
            required
            autofocus
            color="#ff6666"
            autocapitalize="off"
            autocorrect="off"
            autocomplete="off"
            class="mb-0 pb-0"
          ></v-text-field>
          <div v-for="item in search_items" :key="item.name">
            <v-text-field
              @click="
                $router.push({
                  name: 'SearchedView',
                  params: { keyword: Object.keys(item)[0] },
                })
              "
              :value="
                `# ${Object.keys(item)[0]} - 관련 게시물 ${
                  Object.values(item)[0]
                } 개`
              "
              color="#ff6666"
              readonly
            ></v-text-field>
          </div>
          <div v-for="search_user in search_users" :key="search_user.uid">
            <v-text-field
              @click="
                $router.push({
                  name: 'UserDetail',
                  params: { uid: search_user.uid },
                })
              "
              :value="
                ` ${search_user.unick} - 작성 게시물 ${search_user.cnt} 개`
              "
              color="#ff6666"
              readonly
            ></v-text-field>
          </div>
          <span v-show="keyword == ''" v-for="num in lotto" :key="num">
            <v-btn
              color="#ffb6b6"
              class="white--text mr-1 mb-1"
              @click="(keyword = recommends[num]), delaySearch(), lottoNum()"
            >
              # {{ recommends[num] }}
            </v-btn>
          </span>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SearchingView",
  components: {},
  data() {
    return {
      keyword: "",
      keyword2: "",
      timer: null,
      search_items: [],
      search_users: [],
      recommends: [
        "",
        "간단",
        "편의점",
        "디저트",
        "후식",
        "맛있어",
        "스까무라",
        "저렴이",
        "이국적",
        "조합",
        "자취",
        "야식",
        "배고파",
        "초간단",
        "자취요리",
        "매운맛",
      ],
      lotto: [],
    };
  },
  methods: {
    lottoNum() {
      let lotto = [];
      let i = 0;
      while (i < 3) {
        let n = Math.floor(Math.random() * 14) + 1;
        if (!sameNum(n)) {
          lotto.push(n);
          i++;
        }
      }
      function sameNum(n) {
        return lotto.find((e) => e === n);
      }
      this.lotto = lotto;
    },
    // delaysearch
    delaySearch() {
      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }
      const obj = this;
      this.timer = setTimeout(function() {
        obj.search();
      }, 500);
    },
    ...mapActions("feeds", ["searchKeyword"]),
    // search 여기 만들든지 (검색어 전달하는 함수) mapActions 같은걸로 하든지
    search() {
      if (this.keyword != "") {
        this.searchKeyword(this.keyword)
          .then((res) => {
            console.log("res", res);
            this.search_items = res.data.hashtag;
            this.search_users = res.data.unick;
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        this.search_items = [];
        this.search_users = [];
      }
    },
    movetoSearched() {
      if (this.keyword.replace(/^#/g, "").replace(/ /g, "")) {
        this.$router.push({
          name: "SearchedView",
          params: {
            keyword: this.keyword.replace(/^#/g, "").replace(/ /g, ""),
          },
        });
      }
    },
  },
  mounted() {
    this.$emit("change-page", 2);
    this.lottoNum();
    console.log(this.lotto);
  },
};
</script>

<style scoped></style>
