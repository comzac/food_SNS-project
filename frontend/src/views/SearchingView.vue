<template>
  <v-container fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-text-field
          v-model="keyword"
          append-icon="mdi-magnify"
          @input="delaySearch()"
          @click:append-outer="$router.push({ name: 'SearchedView', params: { keyword: keyword } })"
          @keyup.enter="$router.push({ name: 'SearchedView', params: { keyword: keyword } })"
          label="검색어를 입력하세요"
          required
          autofocus
          color="#ff6666"
          autocapitalize="off"
          autocorrect="off"
          class="mb-0 pb-0"
        ></v-text-field>
        <div v-for="item in items" :key="item.name">
          <v-text-field
            @click="$router.push({ name: 'SearchedView', params: { keyword: item.name } })"
            :value="`#${item.name} - 게시물 ${item.count}`"
            color="#ff6666"
            readonly
          ></v-text-field>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "SearchingView",
  components: {},
  data() {
    return {
      keyword: "",
      timer: null,
      items: [],
      items_test: [
        { name: "치킨", count: "123456" },
        { name: "치즈", count: "1234567" },
      ],
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
        obj.search(obj.keyword);
      }, 500);
    },

    // search 여기 만들든지 (검색어 전달하는 함수) mapActions 같은걸로 하든지
    search(keyword) {
      console.log(keyword);
      if (keyword == "치") {
        this.items.push(this.items_test[0]);
        this.items.push(this.items_test[1]);
      }
      if (keyword == "") {
        this.items = [];
      }
    },
  },
};
</script>

<style>
</style>