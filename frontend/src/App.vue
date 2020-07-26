<template>
  <v-app>
    <Header />
    <v-main>
      <transition name="view">
        <router-view />
      </transition>
    </v-main>
  </v-app>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "App",
  components: {
    Header,
  },
  data: () => ({
    //
  }),
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

* {
  font-family: "Jua", sans-serif;
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