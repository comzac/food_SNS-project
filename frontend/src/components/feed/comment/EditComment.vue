<template>
  <v-row>
    <v-col cols="2">
      <v-list-item-avatar
        class="mr-5"
        :color="comment.user.uprofile ? 'white' : 'grey'"
      >
        <v-icon v-if="!authUserImgRoute" dark>mdi-account</v-icon>
        <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
      </v-list-item-avatar>
    </v-col>
    <v-col cols="8" style="padding: 0px">
      <v-text-field
        style="min-height:40px"
        label="댓글수정"
        type="text"
        v-model="comment.content"
        color="#424242"
        @keyup.enter="editComment()"
        autocomplete="off"
        autofocus
      ></v-text-field>
    </v-col>
    <v-col cols="1" style="padding: 0px; margin: auto">
      <v-icon @click="editComment()">mdi-send</v-icon>
    </v-col>
    <v-col cols="1" style="padding: 0px; margin: auto">
      <v-icon @click="closeEditComment()">mdi-window-close</v-icon>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "EditComment",
  props: {
    comment: Object,
    idx: Number,
    cid: Number,
  },
  components: {},
  computed: {
    ...mapGetters("accounts", ["authUserImgRoute"]),
  },
  methods: {
    ...mapActions("comments", ["updateComment"]),
    editComment() {
      this.comment.editdate = new Date();
      // axios 로  this.comment 전송??
      // emit 해서 comments data 다시 받아오게
      this.updateComment(this.comment)
        .then(() => {
          this.$emit("edit-comment", this.idx);
        })
        .catch((err) => console.log(err.response));
    },
    closeEditComment() {
      this.$emit("close-edit-comment", this.idx);
    },
  },
  created() {
    this.content = this.comment.content;
  },
};
</script>

<style></style>
