# AGENTS.md

Guidance for AI coding agents (Codex, Cursor, Copilot, Claude Code, …) working in this repository. `CLAUDE.md` imports this file, so keep all shared guidance here.

## First step: ask for a message dump

**Before writing filters or message classes, ask the user for a chat recording made on the target server with the ChatImprover message dump enabled**, unless they already gave you one. An addon is built around the exact structure of one server's messages. A real recording saves a lot of tokens and time otherwise spent guessing formats.

Tell the user how to record it:
1. Start the game with the JVM argument `-Dchatimprover.debug=true` (the launcher's Java arguments field), or in this repo with `./gradlew runClient --args="-debug-chatimprover"`.
2. Join the server.
3. Trigger the messages in question.
4. Send the `logs/chatimprover/messages-*.jsonl` file.

If the flag has no effect, the installed ChatImprover is too old to have the dump. If the user has no recording or says it isn't needed, continue without it.

The first line of the dump documents its format. In short:
- `plain` is `getString()`.
- `tree` holds each node's Java access `path`, `content`, own `style`, `string` and `siblings`.
- `json` is the whole message, decodable with `ComponentSerialization.CODEC`.

## What this is

ChatImprove-template is the minimal example addon for the [ChatImprover](https://github.com/Ayoree/ChatImprover) library mod (Minecraft 26.2, Java 25). It is meant to be copied when starting an addon for a new server. A full real-world addon is [ChatImprove-dexland](https://github.com/Ayoree/ChatImprove-dexland).

**Library reference (read it before writing addon code):** ChatImprover's [AGENTS.md](https://github.com/Ayoree/ChatImprover/blob/main/AGENTS.md) (raw: `https://raw.githubusercontent.com/Ayoree/ChatImprover/main/AGENTS.md`). It describes:
- the message pipeline;
- the public API contract;
- Minecraft 26.2 naming specifics;
- the dump format.

API sources: [`org.ayoree.chatimprover.api`](https://github.com/Ayoree/ChatImprover/tree/main/src/main/java/org/ayoree/chatimprover/api). Use only classes from that package; everything else in ChatImprover is internal.

## Build & run

Requires a local JDK 25. Versions live in `gradle.properties`; `chat_improver_version` must be a published ChatImprover `mod_version`.

| Command | Purpose |
|---|---|
| `./gradlew build` | Compile + license header check → `build/libs/`. CI runs this on every push/PR. |
| `./gradlew runClient` | Dev client with ChatImprover on the classpath. |
| `./gradlew runClient --args="-debug-chatimprover"` | Same, with the ChatImprover message dump enabled. |
| `./gradlew applyLicenses` | Add the header from `HEADER.txt` to Java files; `build` fails without it. |

- **Where ChatImprover comes from:** GitHub Packages `https://maven.pkg.github.com/Ayoree/ChatImprover`. It needs a token in the env var `AYOREE_GET_PACKAGES_KEY`; CI takes it from the `github-packages` environment secret. `mavenLocal()` is checked first, so `./gradlew publishToMavenLocal` in a checkout of the ChatImprover repo lets an addon build against unreleased library changes.
- **Tests:** none. Providers only activate on a multiplayer server whose address is in `IPs`.

## Structure

- `ChatImprove_template`: `MOD_ID` and `IPs`, the server addresses the addon is active on. A portless address also matches `:25565`; matching is exact on what the player typed in the server list.
- `AddonInformerImpl`: base class every provider extends; supplies `addonID()` (must equal the Fabric mod id) and `IPs()`.
- `FilterImpl`: example filters. Each nested `@AutoService(Provider.class)` class implements `Filter.Provider`; `validator()` returning true **blocks** the message.
- `messages/ExampleMessage`: a plain `ChatMessage` with its nested `ProviderImpl`. `validator()` selects the message, `creator()` builds the class, and `generateChangedMsg()` writes `m_changedMsg` from `m_message`, then returns `this`.
- `messages/ExampleMessageWithSender`: extends `ChatMessageWithSender` and sets the sender nick in its constructor. ChatImprover then prepends a clickable button that opens its command panel for that nick.

## Rules every addon must follow

- **Provider registration:** annotate every provider with `@AutoService(Provider.class)`; without it the provider is silently never loaded.
- **Validators:**
  - They run for every system chat message on the server, so keep them cheap.
  - Check `getSiblings().size()` before `get(i)`: ChatImprover does not catch exceptions from validators or constructors.
  - A constructor may only rely on what its validator guaranteed.
  - The first matching provider wins and the order between providers is undefined, so validators must be mutually exclusive.
- **Own `ClientReceiveMessageEvents.ALLOW_GAME` listeners:**
  - Register them in `ChatImproverApi.ADDON_PHASE`; otherwise ChatImprover may cancel the event before they run.
  - Return early for action-bar (`overlay`) messages.

## Starting a new addon from this template

1. Pick a mod id (convention: `chatimprove_<server>`). Replace `chatimprove_template` in:
   - `fabric.mod.json` (`id`, `icon` path, entrypoint, `name`, `description`, `contact.sources`);
   - `MOD_ID`;
   - `archives_base_name`;
   - the `assets/chatimprove_template/` folder name.
2. Rename the package `org.ayoree.chatimprove.template` and the main class.
3. Set `IPs`. Update `HEADER.txt` (first lines name the addon), then run `./gradlew applyLicenses`.
4. Keep `depends.chatimprover` and `custom.modmenu.parent: "chatimprover"` in `fabric.mod.json`.
5. Replace the example filters and messages with real ones derived from the server's message dump.

## Conventions

- Every `.java` file starts with the AGPL-3.0 header from `HEADER.txt` (enforced by the licenser).
- Minecraft code uses Mojang official names (no Yarn), e.g. `Component`, `ClickEvent.SuggestCommand`, `HoverEvent.ShowText`. See the library's AGENTS.md for 26.2 API shapes.
