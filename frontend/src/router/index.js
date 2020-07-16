import Vue from "vue";
import VueRouter from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";
import PasswordChoiceEmail from "../views/user/PasswordChoiceEmail.vue";
import PasswordChange from "../views/user/PasswordChange.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginView
  },
  {
    path: "/signup",
    name: "Signup",
    component: SignupView
  },
  {
    path: "/user/password_choice",
    name: "PasswordChoice",
    component: PasswordChoice
  },
  {
    path: "/user/password_choice_email",
    name: "PasswordChoiceEmail",
    component: PasswordChoiceEmail
  },
  {
    path: "/user/password_change",
    name: "PasswordChange",
    component: PasswordChange
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
