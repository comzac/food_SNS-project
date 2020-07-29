<template>
  <div class="overflow-hidden">
    <v-app-bar app color="#ff6666" dark height="56px">
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <router-link :to="{name: 'Home'}">
        <v-img
          class="shrink mr-2"
          contain
          src="@/assets/honeycombo.png"
          transition="scale-transition"
          width="100"
        />
      </router-link>
      <v-spacer></v-spacer>

      <router-link class="text-decoration-none" :to="{name: 'Login'}">
        <v-btn icon v-if="!isLoggedIn" class="mr-n3">
          <v-icon>mdi-login</v-icon>
        </v-btn>
      </router-link>
      <router-link class="text-decoration-none" :to="{name: 'FeedCreateView'}">
        <v-btn icon v-if="isLoggedIn" class="mr-n3">
          <v-icon>mdi-lead-pencil</v-icon>
        </v-btn>
      </router-link>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      fixed
      temporary
      color="#ff6666"
      class="whtie--text"
      width="200px"
    >
      <v-list nav dense>
        <v-spacer>
          <br />
        </v-spacer>
        <router-link
          :to="{ name: 'UserDetail', params: { uid: 'asdf' } }"
          class="text-decoration-none"
        >
          <!-- 추후에 params.uid는 로그인 한 유저의 uid으로 바꿔야 한다 -->
          <v-list-item two-line>
            <v-list-item-avatar>
              <img src="@/assets/profile_default.png" />
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="white--text">닉네임 우파루파</v-list-item-title>
              <v-list-item-subtitle class="white--text">
                <small>fishfish99</small>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <v-spacer>
          <br />
        </v-spacer>
        <v-list-item-group active-class="white--text" mandatory>
          <div v-for="listItem in listItemData" :key="listItem.id">
            <NavigationListItem v-if="listItem.title !== 'Account'" :listItem="listItem" />
            <PasswordCheckModal v-if="listItem.title === 'Account'" :listItem="listItem" />
          </div>
        </v-list-item-group>
      </v-list>
      <v-list color="#ff6666" flat align="center">
        <v-btn class="red--text text--lighten-2" rounded @click="logout">
          <strong>로그아웃</strong>
        </v-btn>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import NavigationListItem from "./NavigationListItem";
import PasswordCheckModal from "./PasswordCheckModal";

export default {
  name: "Header",
  components: {
    NavigationListItem,
    PasswordCheckModal,
  },
  data: () => ({
    drawer: false,
    listItemData: [
      { id: "1", link: { name: "Home" }, icon: "mdi-home", title: "Home" },
      {
        id: "2",
        link: { name: "UserEdit" },
        icon: "mdi-account",
        title: "Account",
      },
      {
        id: "3",
        link: { name: "SearchingView" },
        icon: "mdi-magnify",
        title: "Search",
      },
      {
        id: "4",
        link: { name: "" },
        icon: "mdi-bell",
        title: "Notification",
      },
      { id: "5", link: { name: "" }, icon: "mdi-email", title: "Messages" },
      { id: "6", link: { name: "" }, icon: "mdi-cog", title: "Settings" },
    ],
  }),
  computed: {
    ...mapGetters("accounts", ["isLoggedIn"]),
  },
  methods: {
    ...mapActions("accounts", ["logout"]),
  },
};
</script>
