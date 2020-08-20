<template>
  <div>
    <v-list-item two-line>
      <v-list-item-avatar :color="imgRoute ? 'white' : 'grey'">
        <v-icon v-if="!imgRoute" dark>mdi-account</v-icon>
        <v-img v-if="imgRoute" :src="imgRoute" />
      </v-list-item-avatar>
      <v-list-item-content
        class="follow-list-item"
        @click="$router.push({ name: 'UserDetail', params: { uid: uid } })"
      >
        <v-list-item-title>{{ unick }}</v-list-item-title>
        <v-list-item-subtitle>
          <small>{{ uid }}</small>
        </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-action>
        <v-chip
          v-if="!isFollow && !isMe"
          color="#2699fb"
          dark
          @click="follow(uid)"
          >팔로우</v-chip
        >
        <v-chip
          v-if="isFollow && !isMe"
          color="#ff6666"
          outlined
          @click="follow(uid)"
          >언팔로우</v-chip
        >
      </v-list-item-action>
    </v-list-item>
    <v-divider></v-divider>
  </div>
</template>

<script>
import swal from "sweetalert";
import { mapState, mapActions } from "vuex";
import SERVER from "@/api/api";

export default {
  name: "UserFollowListItem",
  data() {
    return {
      isFollow: this.user.isFollowing,
    };
  },
  props: {
    user: Object,
  },
  computed: {
    ...mapState("accounts", ["userSimpleData"]),
    imgName() {
      if (this.user.dbProfile) {
        return this.user.dbProfile.name;
      } else return false;
    },
    imgRoute() {
      if (this.imgName) {
        return SERVER.MEDIA_DIR + this.imgName;
      } else return false;
    },
    unick() {
      return this.user.unick;
    },
    uid() {
      if (this.$route.name === "Follower") {
        return this.user.uid;
      } else return this.user.relationuid;
    },
    isMe() {
      if (this.userSimpleData.unick === this.unick) {
        return true;
      } else return false;
    },
  },
  methods: {
    ...mapActions("accounts", ["sendFollow"]),
    follow() {
      if (this.isFollow) {
        swal({
          text: `${this.unick}님의 팔로우를 취소하시겠습니까?`,
          buttons: ["취소", "확인"],
          dangerMode: true,
        }).then((willDelete) => {
          if (willDelete) {
            this.sendFollow(this.uid);
            swal({
              text: "팔로우가 취소되었습니다.",
              icon: "success",
              dangerMode: true,
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
      }
    },
  },
};
</script>

<style scoped>
.follow-list-item {
  padding-left: 1vw;
}

.follow-list-item:hover {
  cursor: pointer;
  background-color: gainsboro;
}
</style>
