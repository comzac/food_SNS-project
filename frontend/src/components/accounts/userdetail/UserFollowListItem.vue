<template>
  <div>
    <v-list-item two-line>
      <v-list-item-avatar :color="imgData ? 'white' : 'grey'">
        <v-icon v-if="!imgData" dark>mdi-account</v-icon>
        <v-img v-if="imgData" :src="`data:${imgType};base64,${imgData}`" :alt="imgName" />
      </v-list-item-avatar>
      <v-list-item-content>
        <v-list-item-title>{{ unick }}</v-list-item-title>
        <v-list-item-subtitle>
          <small>{{ authUserUid }}</small>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-divider></v-divider>
  </div>
</template>

<script>
export default {
  name: "UserFollowListItem",
  props: {
    user: Object,
  },
  computed: {
    imgData() {
      if (this.user.uprofile) {
        return this.user.uprofile.data;
      } else return false;
    },
    imgType() {
      if (this.user.uprofile) {
        return this.user.uprofile.type;
      } else return false;
    },
    imgName() {
      if (this.user.uprofile) {
        return this.user.uprofile.name;
      } else return false;
    },
    unick() {
      return this.user.uid;
    },
    isFollow() {
      return this.user.isFollowing;
    },
  },
};
</script>

<style>
</style>