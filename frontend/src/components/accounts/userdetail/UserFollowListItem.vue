<template>
  <div>
    <v-list-item two-line>
      <v-list-item-avatar :color="imgData ? 'white' : 'grey'">
        <v-icon v-if="!imgData" dark>mdi-account</v-icon>
        <v-img v-if="imgData" :src="`data:${imgType};base64,${imgData}`" :alt="imgName" />
      </v-list-item-avatar>
      <v-list-item-content
        class="follow-list-item"
        @click="$router.push({name:'UserDetail', params: {uid: uid}})"
      >
        <v-list-item-title>{{ unick }}</v-list-item-title>
        <v-list-item-subtitle>
          <small>{{ uid }}</small>
        </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-action>
        <v-chip v-if="!isFollow && !isMe" color="#2699fb" dark @click="follow(uid)">Follow</v-chip>
        <v-chip v-if="isFollow && !isMe" color="#ff6666" outlined @click="follow(uid)">Unfollow</v-chip>
      </v-list-item-action>
    </v-list-item>
    <v-divider></v-divider>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

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
    imgData() {
      if (this.user.dbProfile) {
        return this.user.dbProfile.data;
      } else return false;
    },
    imgType() {
      if (this.user.dbProfile) {
        return this.user.dbProfile.type;
      } else return false;
    },
    imgName() {
      if (this.user.dbProfile) {
        return this.user.dbProfile.name;
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
        const doubleCheck = confirm(
          `${this.unick}님의 팔로우를 취소하시겠습니까?`
        );
        if (doubleCheck) {
          this.sendFollow(this.uid);
          alert("팔로우가 취소되었습니다.");
          this.isFollow = !this.isFollow;
        }
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