<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card
            :class="`elevation-${hover ? 24 : 6}`"
            class="transition-swing ma-auto"
            max-width="614"
            outlined
          >
            <v-list-item>
              <router-link
                :to="{ name: 'UserDetail', params: { uid: 'fortest' } }"
                class="text-decoration-none"
              >
                <!-- 추후에 params.uid은 피드의 uid으로 바꿔야함. -->
                <v-list-item-avatar color="#ff6666">
                  <img src="@/assets/profile_default.png" width="40" />
                </v-list-item-avatar>
              </router-link>
              <router-link
                :to="{ name: 'UserDetail', params: { uid: 'fortest' } }"
                class="text-decoration-none"
              >
                <v-list-item-content>
                  <v-list-item-title class="text-left red--text text--lighten-2">{{ user.unick }}</v-list-item-title>
                  <v-list-item-subtitle class="text-left red--text text--lighten-2">{{ user.uid }}</v-list-item-subtitle>
                </v-list-item-content>
              </router-link>
              <v-spacer></v-spacer>
            </v-list-item>
            <v-img
              class="white--text text-right"
              height="300px"
              src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
              <v-spacer>
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
              </v-spacer>
              <span>Like_</span>
              <span>2387</span>
              <v-btn icon color="grey">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <v-btn icon color="#ff6666">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
            </v-img>
            <v-card-text>
              <p class="text-left">
                <v-row class="space-around mx-0">
                  <strong>{{ feed.title }}</strong>
                  <v-spacer></v-spacer>
                  <small>{{ ymd2 }}</small>
                </v-row>
              </p>
              <p
                :class="overflow"
                @click="$router.push({ name: 'FeedView', params: { fid: feed.id } })"
              >{{ feed.content }}</p>
              <div class="text-left">
                <p>#{{ feedhashtag.fid }}</p>
              </div>
              <!-- Comment module ?? -->
              <Comment :fid="feed.id" />
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Comment from "@/components/Comment";

export default {
  name: "FeedItem",
  components: {
    Comment,
  },
  props: {
    feed: Object,
    feedhashtag: Object,
    feedlike: Object,
  },
  computed: {
    ymd2() {
      if (this.ymd < 60) {
        return `${this.ymd}초 전`;
      } else if (this.ymd < 3600) {
        return `${parseInt(this.ymd / 60)}분 전`;
      } else if (this.ymd < 86400) {
        return `${parseInt(this.ymd / 3600)}시간 전`;
      } else if (this.ymd < 86400 * 2) {
        return `어제`;
      } else if (this.ymd < 86400 * 7) {
        return `${parseInt(this.ymd / 86400)}일 전`;
      } else {
        return `${parseInt(this.ymd / 604800)}주 전`;
      }
    },
  },
  data() {
    return {
      overflow: "text-left text-overflow",
      hashtag: {},
      ymd:
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(this.feed.regdate).getTime() / 1000),
      user: {
        // 받아오는 것만 남긴다
        id: this.feed.uid,
        uid: "fish87",
        upw: "",
        unick: "Whoever Kim",
        uemail: "",
        uregdate: "",
        ubirth: "",
        usex: "",
        roles: "",
      },
    };
  },
};
</script>

<style scoped>
p.text-overflow {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 222px;
}
</style>
