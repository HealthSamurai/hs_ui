(ns hs-ui.svg.laptop-gear)

(def svg
  [:svg
   {:width "16",
    :height "16",
    :viewBox "0 0 16 16",
    :fill "none",
    :xmlns "http://www.w3.org/2000/svg"}
   [:g
    [:path
     {:d
      "M7.41126 3.11102C7.33902 2.54537 7.55767 2.06601 7.95773 1.70963C8.40839 1.30807 8.97077 1.22857 9.49751 1.53195C9.67287 1.63319 9.88675 1.94812 10.0917 1.89207C10.333 1.82616 10.4475 1.82242 10.5207 1.54928C11.015 -0.287986 13.7238 0.471653 13.2295 2.40608C13.1885 2.56677 13.3107 2.71558 13.4303 2.82769C13.6156 3.00163 13.7324 2.90583 13.9348 2.87593C14.5802 2.77979 15.1799 3.17014 15.3902 3.83091C15.4825 4.1207 15.5347 4.42442 15.4736 4.74173C15.3746 5.25677 15.0163 5.65697 14.542 5.80985C14.4039 5.85435 14.2913 5.9009 14.2556 6.04053C14.2031 6.24606 14.1238 6.40981 14.2986 6.58987C14.755 7.0604 14.8393 7.80815 14.5035 8.36802C14.324 8.66744 14.0761 8.89902 13.7597 9.06277C13.75 9.06798 13.7451 9.07636 13.7451 9.08791C13.7438 9.94562 13.7439 10.803 13.7454 11.66C13.7454 11.7103 13.7561 11.758 13.7776 11.803C14.0052 12.2816 14.2316 12.7606 14.4568 13.2401C14.5722 13.4856 14.6437 13.6746 14.6713 13.8071C14.8193 14.5189 14.4061 15.2625 13.7365 15.4518C13.623 15.4839 13.4505 15.5 13.219 15.5C9.50504 15.5 5.79107 15.5 2.0771 15.5C1.78111 15.5 1.57232 15.4826 1.45075 15.4477C0.644574 15.2163 0.275384 14.2328 0.642028 13.4501C0.899401 12.9008 1.15794 12.3524 1.41765 11.8047C1.43971 11.7583 1.45075 11.7092 1.45075 11.6573C1.4516 9.63998 1.45181 7.62254 1.45138 5.60499C1.45117 5.28111 1.46029 5.05519 1.47875 4.92723C1.60511 4.05344 2.23719 3.33762 3.05545 3.16096C3.18955 3.1322 3.40077 3.11771 3.68912 3.11748C4.92909 3.11657 6.16822 3.11646 7.40649 3.11714L7.40857 3.11668L7.41025 3.11531L7.41121 3.11329L7.41126 3.11102ZM12.6009 3.49696C12.247 3.11544 12.1108 2.63302 12.2311 2.1095C12.2878 1.86319 12.2356 1.71575 12.001 1.64542C11.6522 1.54079 11.5831 1.64271 11.4794 1.97632C11.2906 2.58308 10.8014 2.90379 10.2228 2.98668C9.87635 3.03651 9.55745 2.9552 9.26613 2.74276C9.06244 2.59429 8.91985 2.28752 8.62577 2.55726C8.44563 2.72237 8.33997 2.92451 8.54207 3.14058C8.89153 3.51394 9.02934 4.02694 8.89916 4.54571C8.86161 4.69519 8.82501 4.85452 8.76549 4.9826C8.59023 5.3588 8.3127 5.60918 7.9329 5.73375C7.63723 5.83057 7.59904 5.95797 7.68052 6.26984C7.7534 6.54876 7.89948 6.56507 8.14296 6.50868C8.76772 6.36395 9.3301 6.6799 9.68051 7.23128C9.90563 7.58551 9.9695 7.9762 9.87211 8.40336C9.85004 8.50097 9.84389 8.57322 9.85365 8.6201C9.89725 8.82632 10.0395 8.83753 10.2028 8.88883C10.3724 8.94251 10.5347 8.86471 10.5825 8.67786C10.7251 8.11696 11.0647 7.73341 11.5882 7.59514C11.6916 7.56762 11.8021 7.53093 11.8976 7.51904C12.313 7.46808 12.6756 7.59615 12.9854 7.90327C13.2314 8.14686 13.3756 8.07755 13.5774 7.84178C13.7282 7.66546 13.6942 7.5136 13.5487 7.35495C13.2031 6.9775 13.0736 6.46655 13.2088 5.94574C13.3613 5.36004 13.6079 4.95441 14.1811 4.77163C14.3753 4.7098 14.508 4.60856 14.4606 4.37414C14.4004 4.0779 14.3119 3.91279 14.0007 3.99127C13.4138 4.13939 13.0042 3.93147 12.6009 3.49696ZM8.34633 7.5948C6.71012 8.06261 5.89122 5.49696 7.40394 4.75566C7.53093 4.69349 7.7935 4.69077 7.84411 4.4584C7.84644 4.4473 7.8423 4.44175 7.83169 4.44175C6.43556 4.4422 5.03932 4.44198 3.64297 4.44107C3.4588 4.44084 3.32025 4.45636 3.22732 4.48761C2.98204 4.57006 2.82078 4.73652 2.74355 4.98702C2.7096 5.09709 2.69263 5.30172 2.69263 5.60091C2.69263 7.47306 2.69273 9.34305 2.69295 11.2109C2.69295 11.2154 2.69462 11.2197 2.69761 11.2229C2.70059 11.2261 2.70464 11.2279 2.70886 11.2279H12.4873C12.4979 11.2279 12.5032 11.2222 12.5032 11.2109V8.93435C12.5032 8.92235 12.4991 8.91238 12.4908 8.90446C12.4161 8.83266 12.3406 8.76177 12.2642 8.69179C12.1153 8.55556 11.9778 8.6252 11.8186 8.67208C11.6076 8.73391 11.5873 8.95508 11.5166 9.14329C11.2585 9.83023 10.6009 10.1278 9.94372 9.96034C9.10254 9.74563 8.66683 9.05088 8.86797 8.12749C8.92908 7.84654 8.614 7.51802 8.34633 7.5948ZM1.79352 13.9712C1.76392 14.0344 1.70472 14.0928 1.7687 14.1625C1.77633 14.1709 1.78567 14.175 1.7967 14.175C5.6558 14.175 9.5148 14.175 13.3737 14.175C13.4246 14.175 13.4813 14.1309 13.4555 14.0755C13.2193 13.5723 12.9838 13.0696 12.7489 12.5674C12.7443 12.5577 12.7368 12.5528 12.7267 12.5528H2.47079C2.46188 12.5528 2.45531 12.5571 2.45106 12.5657C2.23209 13.0357 2.01291 13.5042 1.79352 13.9712Z",
      :fill "currentColor"}]
    [:path
     {:d
      "M13.0498 5.29999C13.0498 5.83042 12.8391 6.33913 12.464 6.7142C12.0889 7.08927 11.5802 7.29999 11.0498 7.29999C10.7872 7.29999 10.5271 7.24826 10.2844 7.14775C10.0418 7.04724 9.82131 6.89992 9.63559 6.7142C9.44987 6.52848 9.30256 6.30801 9.20205 6.06535C9.10154 5.8227 9.0498 5.56263 9.0498 5.29999C9.0498 5.03734 9.10154 4.77727 9.20205 4.53462C9.30256 4.29197 9.44987 4.07149 9.63559 3.88577C9.82131 3.70006 10.0418 3.55274 10.2844 3.45223C10.5271 3.35172 10.7872 3.29999 11.0498 3.29999C11.5802 3.29999 12.0889 3.5107 12.464 3.88577C12.8391 4.26085 13.0498 4.76955 13.0498 5.29999ZM11.8484 5.29999C11.8484 5.08828 11.7643 4.88525 11.6146 4.73556C11.4649 4.58586 11.2619 4.50176 11.0502 4.50176C10.8385 4.50176 10.6354 4.58586 10.4857 4.73556C10.336 4.88525 10.2519 5.08828 10.2519 5.29999C10.2519 5.51169 10.336 5.71472 10.4857 5.86442C10.6354 6.01412 10.8385 6.09822 11.0502 6.09822C11.2619 6.09822 11.4649 6.01412 11.6146 5.86442C11.7643 5.71472 11.8484 5.51169 11.8484 5.29999Z",
      :fill "currentColor"}]]])
