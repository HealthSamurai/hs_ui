CSS_INPUT=tailwind.css
CSS_OUTPUT=resources/public/css/hs_ui.css

.PHONY: npm-install
npm-install:
	npm install

.PHONY: init
init:
	make npm-install

.PHONY: deinit
deinit:
	rm -rf node_modules
	rm -rf package-lock.json
	rm -rf .cpcache
	rm -rf .shadow-cljs
	rm -rf resources/public/js/storybook
	rm -rf resources/public/js/components
	rm -rf "$(CSS_OUTPUT)"

.PHONY: tailwind-watch
tailwind-watch:
	npx tailwindcss -i "$(CSS_INPUT)" -o "$(CSS_OUTPUT)" --watch

.PHONY: tailwind-release
tailwind-release:
	npx tailwindcss -i "$(CSS_INPUT)" -o "$(CSS_OUTPUT)"

.PHONY: shadow-watch
shadow-watch:
	clojure -M:shadow watch components storybook

.PHONY: shadow-release
shadow-release:
	clojure -M:shadow release components storybook

.PHONY: story
story:
	npx storybook dev -p 5535

.PHONY: dev
dev:
	make shadow-watch

.PHONY: build
build:
	make shadow-release
	make tailwind-release

.PHONY: pages
pages:
	make build
	npx storybook build -o _site
