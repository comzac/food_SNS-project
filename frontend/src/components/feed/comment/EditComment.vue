<template>
  <v-row>
    <v-col cols="2">
      <v-list-item-avatar class="ma-auto" :color="authUserImgRoute ? 'white' : 'grey'">
        <v-icon v-if="!authUserImgRoute" dark>mdi-account</v-icon>
        <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
      </v-list-item-avatar>
    </v-col>
    <v-col cols="10">
      <v-text-field
        style="min-height:40px"
        label="댓글수정"
        type="text"
        v-model="comment.content"
        color="#ff6666"
        append-icon="mdi-send"
        @click:append="editComment()"
        @keyup.enter="editComment()"
        autocomplete="off"
        autofocus
      ></v-text-field>
    </v-col>
    <!-- <v-col cols="2">
      <v-icon>mdi-window-close</v-icon>
    </v-col>-->
  </v-row>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  name: "EditComment",
  props: {
    comment: Object,
    cid: String,
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
      console.log("asdfasdf", this.cid);
      this.updateComment(this.comment)
        .then(() => {
          this.$emit("editComment", this.cid);
        })
        .catch((err) => console.log(err.response));
    },
  },
};
</script>

<style></style>
