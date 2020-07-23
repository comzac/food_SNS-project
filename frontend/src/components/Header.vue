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
        <v-btn icon>
          <v-icon>mdi-login</v-icon>
        </v-btn>
      </router-link>
      <router-link class="text-decoration-none" :to="{name: 'Login'}">
        <v-btn icon>
          <v-icon>mdi-lead-pencil</v-icon>
        </v-btn>
      </router-link>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
      color="#ff6666"
      class="whtie--text"
      width="200px"
    >
      <v-list nav dense flat>
        <v-spacer>
          <br />
        </v-spacer>
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
        <v-spacer>
          <br />
        </v-spacer>
        <v-list-item-group active-class="white--text" mandatory>
          <router-link :to="{ name: 'Home' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-home</v-icon>
              </v-list-item-icon>
              <v-list-item-title>어디로가</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link :to="{ name: '' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-account</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Account</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link :to="{ name: '' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-magnify</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Search</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link :to="{ name: '' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-bell</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Notifications</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link :to="{ name: '' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-email</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Messages</v-list-item-title>
            </v-list-item>
          </router-link>
          <router-link :to="{ name: '' }" class="text-decoration-none">
            <v-list-item>
              <v-list-item-icon>
                <v-icon>mdi-cog</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Settings</v-list-item-title>
            </v-list-item>
          </router-link>
        </v-list-item-group>
      </v-list>
      <v-list color="#ff6666" flat align="center">
        <router-link :to="{name:'Login'}" class="text-decoration-none">
          <v-btn class="red--text text--lighten-2" rounded>
            <strong>로그아웃</strong>
          </v-btn>
        </router-link>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "Header",
  components: {},
  data: () => ({
    drawer: false
  }),
  methods: {}
};
</script>
