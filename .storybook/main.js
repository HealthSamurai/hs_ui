const config = {
  stories: [
    "../resources/public/js/storybook/*_stories.js",
    "../src/storybook/**/*.mdx"
  ],
  addons: [
    "@storybook/addon-docs",
    "@storybook/addon-controls",
    "storybook-dark-mode"
  ],
  framework: {
    name: "@storybook/react-webpack5",
    options: {
      builder: {
        useSWC: true,
      }
    }
  },
  features: {
    storyStoreV7: false
  }
};

export default config;
