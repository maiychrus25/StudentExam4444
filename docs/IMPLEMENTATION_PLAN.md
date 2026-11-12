# Exam 44844 implementation

Status: Implemented and verified in a new local development folder.

Goal: satisfy all3 questions with native Kotlin/XML, teaching comments and PDF.
Scope: MainActivity form, StudentCardActivity, one small testable validation object,
XML/resources, tests and learning docs; no sibling edits or backend.

Defaults stated to operator: four fields and gender required; phone ASCII0–9
only, no fixed length or email-format rule; keep leading zero. Example card ID
123456789/class CNTT K43/status studying stay static. Successful submit saves five
Activity properties then sends all five values through explicit Intent; pencil
finishes card to edit form. Bundle preserves Activity values across recreation.

Approach: reuse installed toolchain and native widgets; a separate framework or
RecyclerView would add no benefit to two fixed screens. Original screenshot under
docs/reference. Rollback: remove only this newly created folder after review.

- [x] Add boundary tests for phone and required input, observe RED.
- [x] Implement minimal validation, two commented Activities and native XML.
- [x] Test real-time invalid phone, every blank field, both genders, variable/Intent
  transfer, leading zeros, edit/back and rotation; no assertions removed for fixes.
- [x] Run two rounds, each phone/desktop, light/night,font1.0/1.4; inspect screenshots.
- [x] Build/test/lint/docs-check and inspect actual new files; verify sibling hashes.
- [x] Generate and inspect learning PDF with question-to-code mapping and examples.

Actual implementation note: an outer inset FrameLayout and inner padded vertical
LinearLayout keep the16dp spacing stable on edge-to-edge Android. All five values
travel through Intent; only the name replaces a sample card field. ID/class/status
remain the supplied examples. See SYSTEM_DOCUMENTATION.md for measured results.

Superpowers skill is not installed; record native planning/testing evidence here.
