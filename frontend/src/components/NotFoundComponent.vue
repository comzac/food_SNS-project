<template>
  <h1 class="text-center mt-10">
    Page Not Found
    <div class="my-2">
      <v-btn small @click="moveToHome">홈으로</v-btn>
    </div>
  </h1>
</template>

<script>
export default {
  methods: {
    moveToHome() {
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style></style>
