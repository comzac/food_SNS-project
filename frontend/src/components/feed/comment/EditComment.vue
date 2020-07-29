<template>
  <v-row class="ma-0">
    <v-avatar width="56" height="56">
      <!-- <v-icon>mdi-account</v-icon> -->
      <img src="@/assets/profile_default.png" />
    </v-avatar>
    <v-text-field
      rounded
      outlined
      label="댓글수정"
      type="text"
      v-model="editData"
      color="#ff6666"
      append-icon="mdi-send"
      @click:append="editComment(editData)"
      @keyup.enter="editComment(editData)"
    ></v-text-field>
  </v-row>
</template>

<script>
export default {
  name: "EditComment",
  props: {
    comment: Object,
  },
  components: {},
  data() {
    return {
      editData: this.comment.content,
    };
  },
  methods: {
    editComment(editData) {
      this.comment.content = editData;
      this.comment.editdate = new Date();
      // axios 로  this.comment 전송??
      // emit 해서 comments data 다시 받아오게
      this.$emit("editComment");
    },
  },
};
</script>

<style>
</style>