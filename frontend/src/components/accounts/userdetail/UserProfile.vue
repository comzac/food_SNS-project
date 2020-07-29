<template>
  <v-card class="mx-auto mt-10" flat max-width="975" outlined>
    <v-list-item>
      <v-list-item-avatar color="grey">
        <v-icon dark>mdi-account</v-icon>
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="title">{{unick}}</v-list-item-title>
        <v-list-item-subtitle>{{uid}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-btn v-if="mypage" color="grey" fab small dark>
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-chip v-if="!mypage" @click="alert('동작')">follow</v-chip>
    </v-list-item>

    <v-card-text class="text-center">
      맛잘알입니다
      <br />꿀조합 다이어리
    </v-card-text>

    <v-card-actions class="justify-center">
      <v-btn text>
        <div class="d-flex flex-column">
          <span>140</span>
          <span class="caption font-weight-light">POSTS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text>
        <div class="d-flex flex-column">
          <span>140</span>
          <span class="caption font-weight-light">FOLLOWINGS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text>
        <div class="d-flex flex-column">
          <span>24K</span>
          <span class="caption font-weight-light">FOLLOWERS</span>
        </div>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      mypage: this.userDetailData.mypage,
      unick: this.userDetailData.user.unick,
      uid: this.userDetailData.user.uid,
    };
  },
  props: {
    userDetailData: Object,
  },
  methods: {
    setDocumentTitle() {
      document.title = this.unick + " · HoneyCombo";
    },
  },
  mounted() {
    this.setDocumentTitle();
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>