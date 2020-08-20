<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <PasswordChange
          v-if="isLoggedIn && page == '1'"
          @changePassword="doPasswordReset"
          @pageDown="logout"
        />
        <UserAdditionalDataInput
          v-if="isLoggedIn && page == '2'"
          @pageDown="(page = '1'), setPage(1)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";
import SERVER from "@/api/api";
import axios from "axios";
import swal from "sweetalert";

import { mapActions } from "vuex";

import PasswordChange from "@/components/accounts/passwordchange/PasswordChange";
import UserAdditionalDataInput from "@/components/accounts/useredit/UserAdditionalDataInput";

export default {
  name: "SocialLoginDataInputView",
  components: {
    PasswordChange,
    UserAdditionalDataInput,
  },
  data() {
    return {
      page: cookies.get("page") ? cookies.get("page") : 1,
    };
  },
  methods: {
    ...mapActions("accounts", ["isLoggedIn", "setPage", "logout"]),
    doPasswordReset(password) {
      var changeData = {};
      changeData.upw = password;
      changeData.uid = this.$store.state.accounts.userSimpleData.uid;

      axios
        .put(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwreset,
          changeData
        )
        .then((res) => {
          // console.log("res : ", res);
          if (res.data === "success") {
            this.page = "2";
            this.setPage(this.page);
          } else {
            swal({
              text: "비밀번호 설정 실패",
              icon: "error",
              dangerMode: true,
            });
          }
        })
        .catch((err) => console.log(err.response));
    },
  },
  created() {
    this.setPage(1);
  },
  beforeDestroy() {
    this.setPage(1);
  },
};
</script>

<style></style>
