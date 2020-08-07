<template>
  <v-container fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card max-width="614" class="mx-auto" flat>
          <v-text-field
            v-model="keyword"
            append-icon="mdi-magnify"
            @input="delaySearch()"
            @click:append-outer="$router.push({ name: 'SearchedView', params: { keyword: keyword.replace(/^#/g,'').replace(/ /g, '') } })"
            @keyup.enter="$router.push({ name: 'SearchedView', params: { keyword: keyword.replace(/^#/g,'').replace(/ /g, '') } })"
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
              @click="$router.push({ name: 'SearchedView', params: { keyword: Object.keys(item)[0] } })"
              :value="`# ${Object.keys(item)[0]} - 게시물 ${Object.values(item)[0]} 개`"
              color="#ff6666"
              readonly
            ></v-text-field>
          </div>
          <div v-for="search_user in search_users" :key="search_user.uid">
            <v-text-field
              @click="$router.push({ name: 'UserDetail', params: { uid: search_user.uid } })"
              :value="` ${search_user.unick} - 게시물 ${search_user.cnt} 개`"
              color="#ff6666"
              readonly
            ></v-text-field>
          </div>
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
    };
  },
  methods: {
    // delaysearch
    delaySearch() {
      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }
      const obj = this;
      this.timer = setTimeout(function () {
        obj.search();
      }, 500);
    },
    ...mapActions("feeds", ["searchKeyword"]),
    // search 여기 만들든지 (검색어 전달하는 함수) mapActions 같은걸로 하든지
    search() {
      if (this.keyword != "") {
        this.searchKeyword(this.keyword)
          .then((res) => {
            if (/^#/.test(this.keyword)) {
              console.log("res", res);
              this.search_items = res.data;
              this.search_users = [];
            } else {
              this.search_users = res.data;
              this.search_items = [];
            }
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        this.search_items = [];
        this.search_users = [];
      }
    },
  },
};
</script>

<style>
</style>