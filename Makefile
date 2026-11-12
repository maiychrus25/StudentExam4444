.PHONY: build test lint
build:
	./gradlew assembleDebug
	@echo "Build succeeded"
test:
	./gradlew testDebugUnitTest connectedDebugAndroidTest
lint:
	./gradlew lintDebug --warning-mode=fail
	node scripts/check-docs.js
