TAILWIND_INPUT=tailwind.css
TAILWIND_OUTPUT=resources/public/css/tailwind.css

npm-install:
	npm install

init:
	make npm-install

deinit:
	rm -rf node_modules
	rm -rf package-lock.json
	rm -rf .cpcache
	rm -rf .shadow-cljs
	rm -rf resources/public/js/storybook
	rm -rf resources/public/js/components
	rm -rf "$(TAILWIND_OUTPUT)"

tailwind-watch:
	npx tailwindcss -i "$(TAILWIND_INPUT)" -o "$(TAILWIND_OUTPUT)" --watch

tailwind-release:
	npx tailwindcss -i "$(TAILWIND_INPUT)" -o "$(TAILWIND_OUTPUT)"

shadow-watch:
	clj -M:shadow watch components storybook

shadow-release:
	clj -M:shadow release components storybook

story:
	npx storybook dev -p 5535

dev:
	make shadow-watch

build:
	make shadow-release
	make tailwind-release

pages:
	make build
	npx storybook build -o _site
