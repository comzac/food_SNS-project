<template>
  <v-list-item>
    <v-spacer></v-spacer>
    <v-menu left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
      </template>

      <v-list class="text-center">
        <v-list-item>
          <v-list-item-title class="blue--text text-lighten-2"
            >게시글 수정</v-list-item-title
          >
        </v-list-item>
        <v-list-item>
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 삭제</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 신고</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="blue--text text-lighten-2"
            >취소</v-list-item-title
          >
        </v-list-item>
      </v-list>
    </v-menu>
  </v-list-item>
</template>

<script>
export default {};
</script>

<style></style>
