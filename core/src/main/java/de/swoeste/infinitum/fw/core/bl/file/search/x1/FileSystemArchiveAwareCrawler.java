/*
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. I license this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.swoeste.infinitum.fw.core.bl.file.search.x1;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.ArchiveEntry;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;


/**
 * @author swoeste
 */
public class FileSystemArchiveAwareCrawler extends FileSystemCrawler {

    private static final byte[] ZIP = { (byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04 };

    /**
     * Constructor for a new FileSystemArchiveAwareCrawler.
     *
     * @param filters
     */
    public FileSystemArchiveAwareCrawler( final List<ResourceFilter> filters ) {
        super( filters );
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs ) throws IOException {
        // FIXME, IMPROVE ERROR HANDLING
        try {

            if ( file.toFile().isFile() ) {
                if ( isArchive( file ) ) {
                    visitArchive( file );
                }

                // add the archive itself
                // TODO what was this used for?
                // final SimpleFile archiveFile = new SimpleFile( file );
                // addResource( archiveFile );
            }

        } catch ( Exception e ) {
            // FIXME LOG
            // TODO Auto-generated catch block
            System.out.println( "Error reading " + file );
            // e.printStackTrace();
        }

        return super.visitFile( file, attrs );
    }

    private boolean isArchive( final Path file ) {
        try (RandomAccessFile raf = new RandomAccessFile( file.toFile(), "r" )) {
            final byte[] magicNumbers = new byte[ZIP.length];
            raf.read( magicNumbers );
            if ( Arrays.equals( magicNumbers, ZIP ) ) {
                // FIXME LOG
                // System.out.println("I have found a ZIP file!");
                return true;
            }
        } catch (final FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private void addResource( final Resource archiveFile ) {
        if ( accept( archiveFile ) ) {
            getFiles().add( archiveFile );
        }
    }

    public void visitArchive( final Path archive ) throws IOException {
        try (final FileInputStream fis = new FileInputStream( archive.toFile() );
                final ZipInputStream zis = new ZipInputStream( new BufferedInputStream( fis ) )) {
            ZipEntry entry;
            while ( ( entry = zis.getNextEntry() ) != null ) {
                if ( !entry.isDirectory() ) {
                    addResource( new ArchiveEntry( archive, entry.getName() ) );
                }
            }
        }

    }

}
