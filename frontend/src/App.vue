<template>
  <v-app>
    <Header />
    <v-content>
      <transition name="view">
        <router-view />
      </transition>
    </v-content>
  </v-app>
</template>

<script>
import Header from "@/components/Header.vue";
export default {
  name: "App",
  components: {
    Header
  },
  data: () => ({
    //
  })
};
</script>
<style scoped>
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