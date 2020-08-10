<template>
  <v-card class="mx-auto mt-10" flat max-width="975" outlined>
    <v-list-item class="justify-center mb-5">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" dark v-bind="attrs" v-on="on" class="mr-5">
            <v-icon left>mdi-trophy</v-icon>
            {{currentTheme}}
          </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item, index) in contestList" :key="index">
            <v-list-item-title>{{ item.theme }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn color="grey" fab small dark class="ml-5">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </v-list-item>
  </v-card>
</template>

<script>
// import swal from "sweetalert";

export default {
  props: {
    contestList: Array,
  },
  computed: {
    currentTheme() {
      return this.contestList[0].theme;
    },
  },
  methods: {},
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}

button.non-active.theme--light.v-btn.v-btn--disabled {
  color: rgba(0, 0, 0, 0.87) !important;
}

.v-list {
  max-height: 35vh;
  overflow-y: auto;
}

.v-menu__content > div:nth-child(1) > div.v-list-item:hover {
  cursor: pointer;
  background-color: #f6f6f6;
}
</style>
