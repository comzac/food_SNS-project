<template>
  <v-list-item>
    <router-link
      :to="{
        name: 'UserDetail',
        params: { uid: user.uid },
      }"
      class="text-decoration-none"
    >
      <v-list-item-avatar color="#ff6666">
        <img src="@/assets/profile_default.png" width="40" />
      </v-list-item-avatar>
    </router-link>
    <router-link
      :to="{
        name: 'UserDetail',
        params: { uid: user.uid },
      }"
      class="text-decoration-none"
    >
      <v-list-item-content>
        <v-list-item-title class="text-left red--text text--lighten-2">{{
          user.unick
        }}</v-list-item-title>
        <v-list-item-subtitle class="text-left red--text text--lighten-2">{{
          user.uid
        }}</v-list-item-subtitle>
      </v-list-item-content>
    </router-link>
    <v-spacer></v-spacer>
    <v-menu v-if="!item" left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </template>

      <v-list class="text-center">
        <v-list-item @click="moveToUpdateFeed">
          <v-list-item-title class="blue--text text-lighten-2"
            >게시글 수정</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="deleteFeedNow">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 삭제</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 신고</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="blue--text text-lighten-2"
            >취소</v-list-item-title
          >
        </v-list-item>
      </v-list>
    </v-menu>
  </v-list-item>
</template>

<script>
export default {
  name: "Writer",
  props: {
    user: Object,
    item: Boolean,
  },
};
</script>

<style></style>
