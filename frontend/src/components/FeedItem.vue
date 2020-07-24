<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-hover v-slot:default="{ hover }">
          <v-card :class="`elevation-${hover ? 24 : 6}`" class="transition-swing">
            <v-list-item>
              <router-link :to="{ name: 'UserDetail' }" class="text-decoration-none">
                <v-list-item-avatar color="#ff6666">
                  <img src="@/assets/profile_default.png" width="40" />
                </v-list-item-avatar>
              </router-link>
              <router-link :to="{ name: 'UserDetail' }" class="text-decoration-none">
                <v-list-item-content>
                  <v-list-item-title class="text-left red--text text--lighten-2">{{ user.unick }}</v-list-item-title>
                  <v-list-item-subtitle class="text-left red--text text--lighten-2">{{ user.uid }}</v-list-item-subtitle>
                </v-list-item-content>
              </router-link>
              <v-spacer></v-spacer>
              <v-menu left bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>

                <v-list class="text-center">
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="blue--text text-lighten-2">게시물로 이동</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="blue--text text-lighten-2">게시글 수정</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="red--text text-lighten-2">게시글 삭제</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="red--text text-lighten-2">게시글 신고</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="blue--text text-lighten-2">취소</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item>
            <v-img
              class="white--text"
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
                <strong>{{ feed.title }}</strong>
              </p>
              <p
                :class="overflow"
                @click="(overflow=='text-left')?overflow='text-left text-overflow':overflow='text-left'"
              >{{ feed.content }}</p>
              <div class="text-left">
                <p>#{{ feedhashtag.fid }}</p>
              </div>
              <!-- Comment module ?? -->
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "FeedItem",
  props: {
    feed: Object,
    feedhashtag: Object,
    feedlike: Object,
  },
  components: {},
  data() {
    return {
      overflow: "text-left text-overflow",
      hashtag: {},
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
