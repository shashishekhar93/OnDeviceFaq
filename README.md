# OnDeviceFAQ SDK

An Android-first, privacy-focused SDK that transforms PDFs, Markdown files, and documentation into an intelligent on-device knowledge assistant.

## Overview

OnDeviceFAQ SDK enables Android applications to provide AI-powered question answering over local documents without requiring cloud processing. The SDK ingests documents, extracts structured content, builds a searchable knowledge base, and allows users to ask natural language questions about the imported content.

All document processing, indexing, retrieval, and AI inference are designed to run directly on the user's device, ensuring privacy, low latency, and offline availability.

## Features

### Document Ingestion

* PDF document support
* Markdown document support
* Local file processing
* Automatic content extraction and parsing

### Knowledge Base Creation

* Structured document segmentation
* Intelligent content chunking
* Local vector storage
* Incremental document indexing

### Semantic Retrieval

* Natural language search
* Context-aware document retrieval
* Similarity-based ranking
* Multi-document knowledge lookup

### On-Device AI

* Offline question answering
* Privacy-preserving inference
* Low-latency responses
* No server dependency

### Android Native

* Kotlin-first SDK
* Jetpack compatible
* Room database integration
* ONNX Runtime support
* Easy integration into existing Android applications

## Example Usage

```kotlin
faqSdk.initialize(context)

faqSdk.importPdf(pdfFile)

val answer = faqSdk.ask(
    "How do scientists know humans are responsible for climate change?"
)
```

## Architecture

```text
Documents
    ↓
Parser
    ↓
Content Extraction
    ↓
Knowledge Index
    ↓
Semantic Retrieval
    ↓
On-Device AI
    ↓
Generated Answer
```

## Use Cases

* Educational applications
* E-learning platforms
* Employee handbooks
* Product documentation
* Healthcare guides
* Legal documents
* Policy manuals
* Enterprise knowledge bases
* Offline help centers

## Vision

The goal of OnDeviceFAQ SDK is to bring NotebookLM-style document intelligence directly to Android devices, enabling applications to deliver secure, offline, and privacy-preserving AI experiences powered entirely by user-owned content.
