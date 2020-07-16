import Vue from "vue";
import VueRouter from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";

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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
