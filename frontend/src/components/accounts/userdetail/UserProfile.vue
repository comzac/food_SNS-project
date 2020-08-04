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
      <v-chip v-if="!mypage && !isFollow" color="#2699fb" dark @click="follow(uid)">Follow</v-chip>
      <v-chip v-if="!mypage && isFollow" color="#ff6666" outlined @click="follow(uid)">Unfollow</v-chip>
    </v-list-item>

    <v-card-text class="text-center">{{ profileText }}</v-card-text>

    <v-card-actions class="justify-center">
      <v-btn text disabled class="non-active">
        <div class="d-flex flex-column">
          <span>{{numPosts}}</span>
          <span class="caption font-weight-light">POSTS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text @click="moveToFollowers">
        <div class="d-flex flex-column">
          <span>{{numFollowers}}</span>
          <span class="caption font-weight-light">FOLLOWERS</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text @click="moveToFollowings">
        <div class="d-flex flex-column">
          <span>{{numFollowings}}</span>
          <span class="caption font-weight-light">FOLLOWINGS</span>
        </div>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      isFollow: this.userDetailData.isfollow,
      numFollowers: this.userDetailData.userFeeds.followerCount,
    };
  },
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
      } else return false;
    },
    imgType() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.type;
      } else return false;
    },
    imgName() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.name;
      } else return false;
    },
    profileText() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.text;
      } else {
        return "";
      }
    },
    numPosts() {
      return this.userDetailData.userFeeds.feedCount;
    },
    numFollowings() {
      return this.userDetailData.userFeeds.followingCount;
    },
  },
  props: {
    userDetailData: Object,
  },
  methods: {
    ...mapActions("accounts", ["sendFollow"]),
    setDocumentTitle() {
      document.title = this.unick + " · HoneyCombo";
    },
    toProfileEdit() {
      this.$router.push({ name: "UserProfileEdit", params: { uid: this.uid } });
    },
    follow() {
      if (this.isFollow) {
        const doubleCheck = confirm(
          `${this.unick}님의 팔로우를 취소하시겠습니까?`
        );
        if (doubleCheck) {
          this.sendFollow(this.uid);
          alert("팔로우가 취소되었습니다.");
          this.isFollow = !this.isFollow;
          this.numFollowers--;
        }
      } else {
        this.sendFollow(this.uid);
        this.isFollow = !this.isFollow;
        this.numFollowers++;
      }
    },
    moveToFollowings() {
      this.$router.push({
        name: "Following",
        params: { uid: this.$route.params.uid },
      });
    },
    moveToFollowers() {
      this.$router.push({
        name: "Follower",
        params: { uid: this.$route.params.uid },
      });
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

button.non-active.theme--light.v-btn.v-btn--disabled {
  color: rgba(0, 0, 0, 0.87) !important;
}
</style>