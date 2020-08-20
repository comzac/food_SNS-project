<template>
  <v-card class="mx-auto mt-10" flat max-width="975" outlined>
    <v-list-item>
      <v-list-item-avatar :color="imgName ? 'white' : 'grey'">
        <v-icon v-if="!imgName" dark>mdi-account</v-icon>
        <v-img v-if="imgName" :src="imgRoute" />
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title class="title">{{ unick }}</v-list-item-title>
        <v-list-item-subtitle>{{ uid }}</v-list-item-subtitle>
      </v-list-item-content>
      <v-btn v-if="mypage" color="grey" fab small dark @click="toProfileEdit">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-chip
        v-if="!mypage && !isFollow"
        color="#2699fb"
        dark
        @click="follow(uid)"
        >팔로우</v-chip
      >
      <v-chip
        v-if="!mypage && isFollow"
        color="#ff6666"
        outlined
        @click="follow(uid)"
        >언팔로우</v-chip
      >
    </v-list-item>

    <v-card-text class="text-center">{{ profileText }}</v-card-text>

    <v-card-actions class="justify-center">
      <v-btn text disabled class="non-active">
        <div class="d-flex flex-column">
          <span>{{ numPosts }}</span>
          <span class="caption font-weight-light">게시글</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text @click="moveToFollowers">
        <div class="d-flex flex-column">
          <span>{{ numFollowers }}</span>
          <span class="caption font-weight-light">팔로워</span>
        </div>
      </v-btn>
      <v-divider vertical></v-divider>
      <v-btn text @click="moveToFollowings">
        <div class="d-flex flex-column">
          <span>{{ numFollowings }}</span>
          <span class="caption font-weight-light">팔로잉</span>
        </div>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import swal from "sweetalert";
import { mapActions } from "vuex";
import SERVER from "@/api/api";

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
    imgName() {
      if (this.userDetailData.userFeeds.user.uprofile) {
        return this.userDetailData.userFeeds.user.uprofile.name;
      } else return false;
    },
    imgRoute() {
      if (this.imgName) {
        return SERVER.MEDIA_DIR + this.imgName;
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
        swal({
          text: `${this.unick}님의 팔로우를 취소하시겠습니까?`,
          dangerMode: true,
          buttons: ["취소", "확인"],
        }).then((willDelete) => {
          if (willDelete) {
            this.sendFollow(this.uid);
            swal({
              text: "팔로우가 취소되었습니다.",
              dangerMode: true,
              icon: "success",
              buttons: [null, "확인"],
            });
            this.isFollow = !this.isFollow;
          }
        });
        // const doubleCheck = confirm(
        //   `${this.unick}님의 팔로우를 취소하시겠습니까?`
        // );
        // if (doubleCheck) {
        //   this.sendFollow(this.uid);
        //   swal({
        //     text: "팔로우가 취소되었습니다.",
        //     dangerMode: true,
        //   });
        //   this.isFollow = !this.isFollow;
        // }
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
