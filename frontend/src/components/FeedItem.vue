<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-card>
          <v-card-title>
            <v-row class="text-left" row>
              <v-btn icon x-large color="#ff6666">
                <img src="@/assets/profile_default.png" width="40" />
              </v-btn>
              <v-text-field
                solo
                dense
                flat
                color="#ff6666"
                readonly
                class="red--text text--lighten-2"
                value="Whoever Kim"
                messages="fish87"
              ></v-text-field>
              <v-spacer></v-spacer>
              <v-menu left bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-horizontal</v-icon>
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
            </v-row>
          </v-card-title>
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
            <span>Like 2,387</span>
            <v-btn icon color="grey">
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon color="#ff6666">
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-img>
          <v-card-text>
            <p class="text-left">
              <strong>Number 10</strong>
            </p>
            <p
              :class="overflow"
              @click="(overflow=='text-left')?overflow='text-left text-overflow':overflow='text-left'"
            >Lorem ipsum dolor sit amet consectetur, adipisicing elit. Eveniet, adipisci ea praesentium facere magni sequi repudiandae unde, mollitia est ut quia voluptatem porro, neque nulla beatae iusto recusandae obcaecati dolor.</p>
            <div class="text-left">
              <p>#치킨</p>
            </div>
            <!-- Comment module ?? -->
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "FeedItem",
  components: {},
  data() {
    return {
      overflow: "text-left text-overflow"
    };
  }
};
</script>

<style scoped>
p.text-overflow {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 400px;
}
</style>
