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

        <div class="home" v-for="datum in searched_data" :key="datum.feed.id">
          <FeedItem :feed="datum.feed" :feedhashtag="datum.feedhashtag" :feedlike="datum.feedlike" />
        </div>
        <v-hover v-slot:default="{ hover }" open-delay="200">
          <v-btn
            :color="hover ? '#ef5656' : '#ff6666'"
            :elevation="hover ? 24 : 2"
            fixed
            small
            bottom
            right
            fab
            @click="top()"
          >
            <v-icon color="#ffffff">mdi-arrow-up-bold</v-icon>
          </v-btn>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// @ is an alias to /src
import FeedItem from "@/components/FeedItem";

export default {
  name: "Home",
  components: {
    FeedItem,
  },
  data() {
    return {
      keyword: this.$route.params.keyword,
      timer: null,
      items: [],
      // 밑에는 테스트 이후에 지우는 거
      items_test: [
        { name: "마요", count: "123" },
        { name: "마라탕", count: "1234" },
      ],
      // feed 를 page 를 증가시키면서 하나씩 받아와야 할 듯
      // default 로 5개 가져온다
      page: 1,
      searched_data: [
        {
          feed: {
            id: 1,
            uid: 1, //?
            title: "치킨",
            content:
              "Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe harum quod exercitationem sapiente rerum deleniti, ipsa nesciunt voluptatum aspernatur similique labore optio commodi inventore expedita ad praesentium vel officia totam?",
            regdate: "2020-07-27T15:00:00.000+00:00",
            editdate: null,
          },
          feedhashtag: {
            fid: 1,
            hid: 1,
          },
          feedlike: {
            uid: 1,
            fid: 1,
          },
        },
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
    // searching view 의 search 와 동일
    search(keyword) {
      console.log(keyword);
      if (keyword == "마") {
        this.items.push(this.items_test[0]);
        this.items.push(this.items_test[1]);
      }
      if (keyword == "") {
        this.items = [];
      }
    },
    searchfeed(keyword) {
      console.log(keyword);
      // 백 연결 해서 키워드 검색 수정 필요
      this.infiniteScroll();
      this.infiniteScroll();
    },
    infiniteScroll() {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 30
      ) {
        // axios 로 받아와야 하는 부분
        const searched_data2 = JSON.parse(
          JSON.stringify(this.searched_data.slice(-1)[0])
        );
        searched_data2.feed.id += 1;
        this.searched_data.push(searched_data2);
        this.page += 1;
      }
    },
    top() {
      scrollTo(0, 0);
    },
  },
  mounted() {
    this.searchfeed(this.keyword);
  },
  created() {
    window.addEventListener("scroll", this.infiniteScroll);
  },
  destroyed() {
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>
