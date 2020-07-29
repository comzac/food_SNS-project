<template>
  <v-card class="mx-auto mt-10" flat max-width="975" outlined>
    <v-list-item>
      <v-list-item-avatar :color="imgData ? 'white' : 'grey'">
        <v-icon v-if="!imgData" dark>mdi-account</v-icon>
        <v-img v-if="imgData" :src="`data:${imgType};base64,${imgData}`" :alt="imgName" />
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="title">{{unick}}</v-list-item-title>
        <v-list-item-subtitle>{{uid}}</v-list-item-subtitle>
      </v-list-item-content>
      <v-btn v-if="mypage" color="grey" fab small dark @click="toProfileEdit">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-chip v-if="!mypage" @click="alert('동작')">follow</v-chip>
    </v-list-item>

    <v-card-text class="text-center">{{ profileText }}</v-card-text>

    <v-card-actions class="justify-center">
      <v-btn text>
        <div class="d-flex flex-column">
          <span>{{numPosts}}</span>
          <span class="caption font-weight-light">POSTS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text>
        <div class="d-flex flex-column">
          <span>{{numFollowings}}</span>
          <span class="caption font-weight-light">FOLLOWINGS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text>
        <div class="d-flex flex-column">
          <span>{{numFollowers}}</span>
          <span class="caption font-weight-light">FOLLOWERS</span>
        </div>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  computed: {
    mypage() {
      return this.userDetailData.mypage;
    },
    unick() {
      return this.userDetailData.userFeeds.user.unick;
    },
    uid() {
      return this.userDetailData.userFeeds.user.uid;
    },
    imgData() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.data;
      } else {
        return false;
      }
    },
    imgType() {
      return this.userDetailData.userFeeds.user.uprofile.type;
    },
    imgName() {
      return this.userDetailData.userFeeds.user.uprofile.name;
    },
    profileText() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.text;
      } else {
        return false;
      }
    },
    numPosts() {
      return this.userDetailData.userFeeds.feedCount;
    },
    numFollowings() {
      return this.userDetailData.userFeeds.followingCount;
    },
    numFollowers() {
      return this.userDetailData.userFeeds.followerCount;
    },
  },
  props: {
    userDetailData: Object,
  },
  methods: {
    setDocumentTitle() {
      document.title = this.unick + " · HoneyCombo";
    },
    toProfileEdit() {
      this.$router.push({ name: "UserProfileEdit", params: { uid: this.uid } });
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