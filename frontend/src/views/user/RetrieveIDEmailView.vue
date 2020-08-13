<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <PasswordChoiceEmail
          v-if="page == '1' && !uid"
          @toEmailVerification="setConfirmCode"
          @go-back="$router.go(-1)"
        />
        <PasswordChoiceEmailVerification
          v-if="page == '2' && !uid"
          @moveToConductPage="moveToConductPage"
          @pageDown="(page = '1'), setPage(1)"
        />
        <RetrieveIDPage v-if="uid" :uid="uid" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";

import { mapActions, mapState } from "vuex";

import PasswordChoiceEmail from "@/components/accounts/passwordchange/PasswordChoiceEmail";
import PasswordChoiceEmailVerification from "@/components/accounts/passwordchange/PasswordChoiceEmailVerification";
import RetrieveIDPage from "@/components/accounts/passwordchange/RetrieveIDPage";

export default {
  name: "RetrieveIDEmailView",
  components: {
    PasswordChoiceEmail,
    PasswordChoiceEmailVerification,
    RetrieveIDPage,
  },
  data() {
    return {
      page: cookies.get("page") ? cookies.get("page") : 1,
      confirmCode: "",
      userEmailData: {
        uemail: this.$store.state.accounts.uemail,
      },
      uid: null,
    };
  },
  computed: {
    ...mapState("accounts", ["foundUid"]),
  },
  methods: {
    ...mapActions("accounts", ["setPage", "setEmail", "setCode", "getUserId"]),
    setConfirmCode(userEmailData) {
      this.userEmailData.uemail = userEmailData.uemail;
      this.setEmail(userEmailData.uemail);
      this.confirmCode = userEmailData.confirmCode;
      this.page = "2";
      this.setPage(2);
    },
    moveToConductPage() {
      const element = this;
      this.getUserId()
        .then((res) => {
          element.uid = res.data.data;
        })
        .catch((err) => {
          console.log(err.response);
        });
      this.setPage(1);
      this.setEmail("");
      this.setCode("");
    },
  },
};
</script>

<style></style>
