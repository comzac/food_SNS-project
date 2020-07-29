<template>
  <v-app>
    <Header />
    <v-main>
      <transition name="view">
        <router-view />
      </transition>
    </v-main>
    <Signup v-if="$route.name=='Login'" />
  </v-app>
</template>

<script>
import { mapActions } from "vuex";
import Header from "@/components/navbar/Header";
import Signup from "@/components/navbar/Signup";

export default {
  name: "App",
  components: {
    Header,
    Signup,
  },
  data: () => ({
    //
  }),
  methods: {
    ...mapActions("accounts", ["getUserSimpleData"]),
  },
  created() {
    this.getUserSimpleData();
  },
  beforeUpdate() {
    this.getUserSimpleData();
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
</style>