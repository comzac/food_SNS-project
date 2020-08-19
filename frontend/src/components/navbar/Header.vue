<template>
  <div class="overflow-hidden">
    <v-app-bar app color="#ea907a" dark height="56px">
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <router-link :to="{ name: 'Home' }">
        <v-img
          class="shrink mr-2"
          contain
          src="@/assets/honeycombo.png"
          transition="scale-transition"
          width="100"
        />
      </router-link>
      <v-spacer></v-spacer>

      <router-link class="text-decoration-none" :to="{ name: 'Login' }">
        <v-btn icon v-if="!isLoggedIn" class="mr-n3">
          <v-icon>mdi-login</v-icon>
        </v-btn>
      </router-link>
      <router-link class="text-decoration-none" :to="{ name: 'FeedCreateView' }">
        <v-btn icon v-if="isLoggedIn" class="mr-n3">
          <v-icon>mdi-lead-pencil</v-icon>
        </v-btn>
      </router-link>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      fixed
      temporary
      color="#ea907a"
      class="whtie--text"
      width="200px"
    >
      <v-list nav dense flat>
        <v-spacer>
          <br />
        </v-spacer>
        <router-link
          :to="{ name: 'UserDetail', params: { uid: authUserUid } }"
          class="text-decoration-none"
        >
          <v-list-item two-line>
            <v-list-item-avatar :color="authUserImgRoute ? 'white' : 'grey'">
              <v-icon v-if="!authUserImgRoute" dark>mdi-account</v-icon>
              <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title class="white--text">
                {{
                authUserUnick
                }}
              </v-list-item-title>
              <v-list-item-subtitle class="white--text">
                <small>{{ authUserUid }}</small>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </router-link>
        <v-spacer>
          <br />
        </v-spacer>
        <v-list-item-group v-model="selection" active-class="white--text" mandatory>
          <div v-for="(listItem, i) in listItemData" :key="i">
            <NavigationListItem
              v-show="i !== 7"
              :listItem="listItem"
              :value="i"
              @clear-item="drawer = false"
            />
            <!-- <PasswordCheckModal
              v-if="listItem.title === 'Account'"
              :listItem="listItem"
              @clear-item="clearItem(), drawer=false"
            />-->
          </div>
        </v-list-item-group>
      </v-list>
      <v-list color="#ea907a" flat align="center">
        <v-btn class="red--text text--lighten-2" rounded @click="logout">
          <strong>로그아웃</strong>
        </v-btn>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import NavigationListItem from "./NavigationListItem";
// import PasswordCheckModal from "./PasswordCheckModal";

export default {
  name: "Header",
  components: {
    NavigationListItem,
    // PasswordCheckModal,
  },
  data: () => ({
    drawer: false,
    listItemData: [
      { id: "1", link: { name: "Home" }, icon: "mdi-home", title: "홈" },
      {
        id: "2",
        link: { name: "UserEdit" },
        icon: "mdi-account",
        title: "계정관리",
      },
      {
        id: "3",
        link: { name: "SearchingView" },
        icon: "mdi-magnify",
        title: "검색",
      },
      {
        id: "4",
        link: { name: "NotificationView" },
        icon: "mdi-bell",
        title: "알림",
      },
      {
        id: "5",
        link: { name: "ContestList" },
        icon: "mdi-trophy",
        title: "꿀조합대회",
      },
      {
        id: "6",
        link: { name: "Liquor" },
        // icon: "mdi-glass-mug-variant",
        icon: "mdi-glass-cocktail",
        title: "칵테일",
      },
    ],
  }),
  computed: {
    ...mapGetters("accounts", [
      "isLoggedIn",
      "authUserImgRoute",
      "authUserUid",
      "authUserUnick",
    ]),
    ...mapGetters("frame", { selectionFromStore: "selection" }),
    selection: {
      get() {
        return this.selectionFromStore;
      },
      set(newSelection) {
        return newSelection;
      },
    },
  },
  methods: {
    ...mapActions("accounts", ["logout"]),
    ...mapMutations("frame", ["SET_SELECTION"]),
    clearItem(item) {
      this.SET_SELECTION(item);
    },
  },
};
</script>
