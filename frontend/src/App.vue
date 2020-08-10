<template>
  <v-app>
    <Header v-if="isLoggedIn" :selection="selection" />
    <v-main>
      <transition name="view">
        <router-view @change-page="changePage" />
      </transition>
    </v-main>
    <transition v-if="$route.name !== 'CommentView'" name="slide-fade">
      <v-btn
        v-show="scrollY"
        color="#ff6666"
        elevation="24"
        fixed
        small
        bottom
        right
        fab
        @click="top()"
        class="mb-14"
      >
        <v-icon color="#ffffff">mdi-arrow-up-bold</v-icon>
      </v-btn>
    </transition>
    <Signup v-if="$route.name == 'Login'" />
    <Bottom v-if="isLoggedIn" v-show="$route.name !== 'FeedCreateView'" />
  </v-app>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Header from "@/components/navbar/Header";
import Bottom from "@/components/navbar/Bottom";
import Signup from "@/components/navbar/Signup";

export default {
  name: "App",
  components: {
    Header,
    Signup,
    Bottom,
  },
  data() {
    return {
      scrollY: false,
      selection: 0,
    };
  },
  computed: {
    ...mapGetters("accounts", ["isLoggedIn"]),
  },
  methods: {
    ...mapActions("accounts", ["getUserSimpleData"]),
    Scroll() {
      if (window.scrollY) {
        this.scrollY = true;
      } else {
        this.scrollY = false;
      }
    },
    top() {
      scrollTo(0, 0);
    },
    changePage(selection) {
      // console.log("changePage", selection, typeof selection);
      this.selection = selection;
    },
  },
  created() {
    this.getUserSimpleData();
    window.addEventListener("scroll", this.Scroll);
  },
  beforeUpdate() {
    this.getUserSimpleData();
  },
  destroyed() {
    window.removeEventListener("scroll", this.Scroll);
  },
};
</script>
<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);
.notosanskr * {
  font-family: "Noto Sans KR", sans-serif;
}

* {
  font-family: "Noto Sans KR", sans-serif;
}
h1 {
  color: #ff6666;
}
.view-enter-active,
.view-leave-active {
  transition: opacity 0s;
}
.view-leave-active {
  position: absolute;
}
.view-enter,
.view-leave-to {
  opacity: 0;
}
.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
</style>
