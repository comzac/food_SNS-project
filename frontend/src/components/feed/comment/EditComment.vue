<template>
  <v-row class="ma-0">
    <v-list-item-avatar :color="authUserImgData ? 'white' : 'grey'">
      <v-icon v-if="!authUserImgData" dark>mdi-account</v-icon>
      <v-img
        v-if="authUserImgData"
        :src="`data:${authUserImgType};base64,${authUserImgData}`"
        :alt="authUserImgName"
      />
    </v-list-item-avatar>
    <v-text-field
      rounded
      outlined
      label="댓글수정"
      type="text"
      v-model="comment.content"
      color="#ff6666"
      append-icon="mdi-send"
      @click:append="editComment()"
      @keyup.enter="editComment()"
      autocomplete="off"
    ></v-text-field>
  </v-row>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "EditComment",
  props: {
    comment: Object,
  },
  components: {},

  computed: {
    ...mapGetters("accounts", [
      "authUserImgData",
      "authUserImgType",
      "authUserImgName",
    ]),
  },
  methods: {
    ...mapActions("comments", ["updateComment"]),
    editComment() {
      this.comment.editdate = new Date();
      // axios 로  this.comment 전송??
      // emit 해서 comments data 다시 받아오게
      this.updateComment(this.comment)
        .then(() => {
          this.$emit("editComment");
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style></style>
