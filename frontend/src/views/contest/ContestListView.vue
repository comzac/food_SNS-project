<template>
  <div>
    <ContestHead v-if="contestList" :contestList="contestList" />
    <ContestFeedList />
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import ContestHead from "@/components/contest/ContestHead";
import ContestFeedList from "@/components/contest/ContestFeedList";

export default {
  data() {
    return {
      overlay: true,
    };
  },
  components: {
    ContestHead,
    ContestFeedList,
  },
  computed: {
    ...mapState("contests", ["contestList"]),
  },
  methods: {
    ...mapActions("contests", ["getRecentContestFeeds", "getContestList"]),
  },
  created() {
    this.getRecentContestFeeds();
    this.getContestList();
    setTimeout(() => {
      this.overlay = false;
    }, 800);
  },
  mounted() {
    this.$emit("change-page", 4);
    window.scrollTo(0, 0);
  },
};
</script>

<style></style>
